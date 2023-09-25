package com.study.orderservice.command.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String userID;
    private String addressId;
    private String productId;
    private Integer quantity;
    private String orderStatus;
}
