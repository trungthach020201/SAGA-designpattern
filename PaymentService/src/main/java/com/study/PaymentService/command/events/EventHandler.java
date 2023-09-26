package com.study.PaymentService.command.events;

import com.study.CommonService.events.PaymentProcessedEvent;
import com.study.PaymentService.command.entity.Payment;
import com.study.PaymentService.command.entity.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class EventHandler {

    private final PaymentRepository paymentRepository;
    @org.axonframework.eventhandling.EventHandler
    public void on (PaymentProcessedEvent event){
        Payment payment = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .timeStamp(new Date())
                .paymentStatus("COMPLETED")
                .build();
        paymentRepository.save(payment);
    }

}
