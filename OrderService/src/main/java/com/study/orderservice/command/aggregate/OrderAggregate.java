package com.study.orderservice.command.aggregate;

import com.study.CommonService.commands.CompleteOrderCommand;
import com.study.CommonService.events.OrderCompletedEvent;
import com.study.orderservice.command.commands.CreateOrderCommand;
import com.study.orderservice.command.events.OrderCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String userId;
    private String addressId;
    private String productId;
    private Integer quantity;
    private String orderStatus;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        //valuidate the date of createOrderCommand here
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand,orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on (OrderCreatedEvent event){
        this.orderId=event.getOrderId();
        this.orderStatus = event.getOrderStatus();
        this.addressId= event.getAddressId();
        this.userId = event.getUserId();
        this.productId=event.getProductId();
        this.quantity=event.getQuantity();
    }

    @CommandHandler
    public void handle (CompleteOrderCommand completeOrderCommand){
        OrderCompletedEvent orderCompletedEvent = OrderCompletedEvent
                .builder()
                .orderId(completeOrderCommand.getOrderId())
                .orderStatus(completeOrderCommand.getOrderStatus())
                .build();
        AggregateLifecycle.apply(orderCompletedEvent);
    }

    public void on (OrderCompletedEvent orderCompletedEvent){
//        this.orderId = orderCompletedEvent.getOrderId();
        this.orderStatus = orderCompletedEvent.getOrderStatus();
    }

}

