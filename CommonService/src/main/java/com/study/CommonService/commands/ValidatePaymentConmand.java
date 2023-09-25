package com.study.CommonService.commands;

import com.study.CommonService.models.CardDetails;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ValidatePaymentConmand {
    private String orderId;
    private String paymentId;
    private CardDetails cardDetails;
}
