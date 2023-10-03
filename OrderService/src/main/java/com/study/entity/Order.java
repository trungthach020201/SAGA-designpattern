package com.study.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_table")
public class Order {
    @Id
    private String orderId;
    private String userId;
    private String addressId;
    private String productId;
    private Integer quantity;
    private String orderStatus;
}
