package com.study.command.events;

import com.study.CommonService.events.PaymentProcessedEvent;
import com.study.command.entity.Payment;
import com.study.command.entity.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class EventHandler {

    private PaymentRepository paymentRepository;

    public EventHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @org.axonframework.eventhandling.EventHandler
    public void on (PaymentProcessedEvent event){
        log.info("=========>Starting save" + event.getPaymentId());
        Payment payment = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .timeStamp(new Date())
                .paymentStatus("COMPLETED")
                .build();
        paymentRepository.save(payment);
    }

}
