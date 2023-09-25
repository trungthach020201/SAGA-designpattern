package com.study.orderservice.command.events;

import lombok.Builder;
import lombok.Data;

@Data
public class OrderCreatedEvent {
    private String orderId;
    private String userID;
    private String addressId;
    private Integer quantity;
    private String productId;
    private String orderStatus;
}
