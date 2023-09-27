package com.study.PaymentService.command.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Payment")
public class Payment {
    @Id
    private String paymentId;
    private String orderId;
    private Date timeStamp;
    private String paymentStatus;

}
