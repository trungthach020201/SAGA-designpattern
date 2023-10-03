package com.study.CommonService.events;

import lombok.Data;

@Data
public class OrderCancelEvent {
    private String orderId;
    private String orderStatus;
}
