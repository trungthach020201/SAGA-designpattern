package com.study.CommonService.commands;

import com.study.CommonService.models.CardDetailsResponse;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class ValidatePaymentConmand {

    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private CardDetailsResponse cardDetailsResponse;
}
