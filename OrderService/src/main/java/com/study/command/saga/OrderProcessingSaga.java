package com.study.command.saga;

import com.study.CommonService.commands.*;
import com.study.CommonService.events.*;
import com.study.CommonService.models.UserResponse;
import com.study.CommonService.queries.GetUserPaymentDetailQuery;
import com.study.command.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.BeanUtils;
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
        System.out.println("======> The userResponse ID at order Service is :" + getUserPaymentDetailQuery.getUserId());
        UserResponse userResponse = null;
        try{
            //get the userResponse information by calling the userResponse service to response
            userResponse = queryGateway.query(getUserPaymentDetailQuery, ResponseTypes.instanceOf(UserResponse.class)).join();
        }catch (Exception e){
            log.error(e.getMessage());
            //starting the Compensation transaction
            cancelOrderCommand(event.getOrderId());
        }
        ValidatePaymentConmand validatePaymentConmand = ValidatePaymentConmand.builder()
                .paymentId(UUID.randomUUID().toString())
                .cardDetailsResponse(userResponse.getCardDetailsResponse())
                .orderId(event.getOrderId())
                .build();
        commandGateway.sendAndWait(validatePaymentConmand);
    }

    private void cancelOrderCommand(String orderId) {
        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(orderId);
        commandGateway.sendAndWait(cancelOrderCommand);
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
            cancelPaymentCommand(event);
        }
    }
    private void cancelPaymentCommand(PaymentProcessedEvent event) {
        CancelPaymentCommand cancelPaymentCommand = new CancelPaymentCommand(event.getPaymentId(), event.getOrderId());

        commandGateway.sendAndWait(cancelPaymentCommand);
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on (ShipmentCompletedEvent event) {
        log.info("ShipmentCompletedEvent in SAGA for Order Id: {}", event.getOrderId());
        try {
            if (true)
                throw new Exception();
            CompleteOrderCommand completeOrderCommand
                    = CompleteOrderCommand.builder()
                    .orderId(event.getOrderId())
                    .orderStatus("APPROVED")
                    .build();
            commandGateway.send(completeOrderCommand);
        }catch (Exception e) {
            log.error(e.getMessage());
            cancelShipmentCommand(event);
        }
    }
    private void cancelShipmentCommand(ShipmentCompletedEvent event) {
        CancelShipmentCommand cancelShipmentCommand = new CancelShipmentCommand();
        BeanUtils.copyProperties(event,cancelShipmentCommand);
        commandGateway.sendAndWait(cancelShipmentCommand);
    }
    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on (OrderCompletedEvent event){
        log.info("OrderCompletedEvent in SAGA for Order Id: {}", event.getOrderId());
    }
    //call rollback center
    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on (OrderCancelEvent event){
        log.info("OrderCancelledEvent in SAGA for Order Id: {}", event.getOrderId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on (PaymentCancelEvent event){
        log.info("PaymentCancelEvent in SAGA for Order Id: {}", event.getOrderId());
        cancelOrderCommand(event.getOrderId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on (ShipmentCancelEvent event){
        log.info("ShipmentCancelEvent in SAGA for Order Id: {}", event.getOrderId());
        PaymentProcessedEvent  paymentProcessedEvent = new PaymentProcessedEvent();
        paymentProcessedEvent.setOrderId(event.getOrderId());
        cancelPaymentCommand(paymentProcessedEvent);
    }

}
