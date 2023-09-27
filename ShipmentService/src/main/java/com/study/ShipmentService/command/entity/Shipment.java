package com.study.ShipmentService.command.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shipment")
public class Shipment {
    @Id
    private String shipId;
    private String orderId;
    private String shipmentStatus;

}
