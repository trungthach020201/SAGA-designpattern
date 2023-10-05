package com.study.CommonService.events;

import lombok.Data;

@Data
public class ShipmentCancelEvent {
    private String shipmentId;
    private String orderId;
    private String paymentId;
    private String shipmentStatus;
}
