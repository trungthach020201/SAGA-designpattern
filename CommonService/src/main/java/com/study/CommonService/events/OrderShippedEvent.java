package com.study.CommonService.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class OrderShippedEvent {
    private String shipId;
    private String orderId;
    private String shipmentStatus;
}
