package com.study.command.events;

import com.study.CommonService.events.PaymentCancelEvent;
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

    @org.axonframework.eventhandling.EventHandler
    public void on (PaymentCancelEvent event){
        log.info("=========>Starting rollback payment id:" + event.getPaymentId());
        Payment payment = paymentRepository.findById(event.getPaymentId()).get();
        payment.setPaymentStatus(event.getPaymentStatus());
        paymentRepository.save(payment);
    }

}
