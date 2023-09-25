package com.study.orderservice.command.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderRestModel {
    private String orderId;
    private String userID;
    private String addressId;
    private String productId;
    private Integer quantity;
}
