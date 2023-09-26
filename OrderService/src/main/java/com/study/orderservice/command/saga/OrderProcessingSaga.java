package com.study.orderservice.command.saga;

import com.study.CommonService.commands.CompleteOrderCommand;
import com.study.CommonService.commands.ShipOrderCommand;
import com.study.CommonService.commands.ValidatePaymentConmand;
import com.study.CommonService.events.OrderCompletedEvent;
import com.study.CommonService.events.OrderShippedEvent;
import com.study.CommonService.events.PaymentProcessedEvent;
import com.study.CommonService.models.User;
import com.study.CommonService.queries.GetUserPaymentDetailQuery;
import com.study.orderservice.command.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;
    public OrderProcessingSaga() {
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    private void handle (OrderCreatedEvent event){
        log.info("OrderCreatedEvent in SAGA for Order Id: {}", event.getOrderId());

        GetUserPaymentDetailQuery getUserPaymentDetailQuery = new GetUserPaymentDetailQuery(event.getUserId());
        System.out.println("======> The user ID at order Service is :" + getUserPaymentDetailQuery.getUserId());
        User user = null;

        try{
            //get the user information by calling the user service to response
            user = queryGateway.query(getUserPaymentDetailQuery, ResponseTypes.instanceOf(User.class)).join();
        }catch (Exception e){
            log.error(e.getMessage());
            //starting the Compensation transaction
        }
        ValidatePaymentConmand validatePaymentConmand = ValidatePaymentConmand.builder()
                .paymentId(UUID.randomUUID().toString())
                .cardDetails(user.getCardDetails())
                .orderId(event.getOrderId())
                .build();
        commandGateway.sendAndWait(validatePaymentConmand);


    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent event){
        log.info("PaymentProcessedEvent in SAGA for Order Id: {}", event.getOrderId());
        try{
            ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
                    .shipId(UUID.randomUUID().toString())
                    .orderId(event.getOrderId())
                    .build();
            commandGateway.sendAndWait(shipOrderCommand);
        }catch (Exception e){
            log.error(e.getMessage());
            //starting the Compensation transaction
        }
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on (OrderShippedEvent event){
        log.info("OrderShippedEvent in SAGA for Order Id: {}", event.getOrderId());

        CompleteOrderCommand completeOrderCommand = CompleteOrderCommand
                .builder()
                .orderStatus("APPROVOED")
                .build();
        commandGateway.sendAndWait(completeOrderCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on (OrderCompletedEvent event){
        log.info("OrderCompletedEvent in SAGA for Order Id: {}", event.getOrderId());
    }
}
