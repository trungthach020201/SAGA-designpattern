package com.study.orderservice.command.events;

import com.study.CommonService.events.OrderCompletedEvent;
import com.study.orderservice.entity.Order;
import com.study.orderservice.entity.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventHandler {

    private final OrderRepository orderRepository;

    @org.axonframework.eventhandling.EventHandler
    public void on (OrderCreatedEvent event){
        Order order = new Order();
        BeanUtils.copyProperties(event,order);
        orderRepository.save(order);
    }

    @org.axonframework.eventhandling.EventHandler
    public void on (OrderCompletedEvent event){
        Order order = orderRepository.findById(event.getOrderId()).get();
            order.setOrderStatus(event.getOrderStatus());
            orderRepository.save(order);
    }
}
