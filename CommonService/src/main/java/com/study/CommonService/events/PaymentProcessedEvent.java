package com.study.CommonService.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessedEvent {
    private String orderId;
    private String paymentId;
}
