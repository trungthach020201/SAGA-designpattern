package com.study.command.aggregate;

import com.study.CommonService.commands.ValidatePaymentConmand;
import com.study.CommonService.events.PaymentProcessedEvent;
import com.study.CommonService.models.CardDetails;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;

    public PaymentAggregate() {
    }

    @CommandHandler
    public PaymentAggregate(ValidatePaymentConmand event) {
        //validate the payment process
        //publish the payment processed event
        log.info("Executing Validatepayment for Order Id: {} and payment Id: {}" + event.getOrderId(), event.getPaymentId());

        PaymentProcessedEvent paymentProcessedEvent = new  PaymentProcessedEvent();
        paymentProcessedEvent.setPaymentId(event.getPaymentId());
        paymentProcessedEvent.setOrderId(event.getOrderId());

        AggregateLifecycle.apply(paymentProcessedEvent);
        log.info("PaymentProcessdEvent is applied");
    }

    @EventSourcingHandler
    public void on (PaymentProcessedEvent event){
        this.orderId = event.getOrderId();
        this.paymentId = event.getPaymentId();
        log.info("event sourcing handler");
    }
}
