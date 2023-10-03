package com.study.command.events;

import com.study.CommonService.events.OrderCancelEvent;
import com.study.CommonService.events.OrderCompletedEvent;
import com.study.entity.OrderRepository;
import com.study.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
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

    @org.axonframework.eventhandling.EventHandler
    public void on (OrderCancelEvent event){
        Order order = orderRepository.findById(event.getOrderId()).get();
        order.setOrderStatus(event.getOrderStatus());
        orderRepository.save(order);
    }
}
