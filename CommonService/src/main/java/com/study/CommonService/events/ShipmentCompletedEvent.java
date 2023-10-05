package com.study.CommonService.events;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class ShipmentCompletedEvent {
    
    private String shipId;
    private String orderId;
    private String shipmentStatus;
}
