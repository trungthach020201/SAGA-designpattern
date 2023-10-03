package com.study.CommonService.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class CancelShipmentCommand {
    @TargetAggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String shipmentStatus = "CANCELLED";
}
