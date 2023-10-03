package com.study.CommonService.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipmentCompletedEvent {
    private String shipId;
    private String orderId;
    private String shipmentStatus;
}
