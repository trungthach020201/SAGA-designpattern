package com.study.CommonService.commands;

import com.study.CommonService.models.CardDetails;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class ValidatePaymentConmand {

    @TargetAggregateIdentifier
    private String orderId;
    private String paymentId;
    private CardDetails cardDetails;
}
