package com.study.command.event;

import com.study.CommonService.events.OrderShippedEvent;
import com.study.command.entity.Shipment;
import com.study.command.entity.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventHandler {
    private final ShipmentRepository shipmentRepository;

    @org.axonframework.eventhandling.EventHandler
    public void on (OrderShippedEvent event){
        Shipment shipment = new Shipment();
        BeanUtils.copyProperties(event,shipment);
        shipmentRepository.save(shipment);
    }
}
