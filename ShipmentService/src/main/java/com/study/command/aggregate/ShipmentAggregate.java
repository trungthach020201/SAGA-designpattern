package com.study.command.aggregate;

import com.study.CommonService.commands.ShipOrderCommand;
import com.study.CommonService.events.ShipmentCompletedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class ShipmentAggregate {
     @AggregateIdentifier
    private String shipId;
    private String orderId;
    private String shipmentStatus;

    public ShipmentAggregate() {
    }

    @CommandHandler
    public ShipmentAggregate(ShipOrderCommand shipOrderCommand) {
        ShipmentCompletedEvent shipmentCompletedEvent = ShipmentCompletedEvent.builder()
                .orderId(shipOrderCommand.getOrderId())
                .shipId(shipOrderCommand.getShipId())
                .shipmentStatus("COMPLETED")
                .build();
        AggregateLifecycle.apply(shipmentCompletedEvent);
    }

    @EventSourcingHandler
    public void on (ShipmentCompletedEvent shipmentCompletedEvent){
        this.orderId= shipmentCompletedEvent.getOrderId();
        this.shipId = shipmentCompletedEvent.getShipId();
        this.shipmentStatus = shipmentCompletedEvent.getShipmentStatus();
    }
}
