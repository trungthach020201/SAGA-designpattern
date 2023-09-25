package com.study.orderservice.command.saga;

import com.study.CommonService.commands.ValidatePaymentConmand;
import com.study.CommonService.models.User;
import com.study.CommonService.queries.GetUserPaymentDetailQuery;
import com.study.orderservice.command.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;

import java.util.UUID;

@Saga
@Slf4j
@RequiredArgsConstructor
public class OrderProcessingSaga {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    private void handle (OrderCreatedEvent event){
        log.info("Order created in SAGA for Order Id: {}", event.getOrderId());
        GetUserPaymentDetailQuery getUserPaymentDetailQuery = new GetUserPaymentDetailQuery(event.getUserID());
        User user = null;

        try{
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
}
