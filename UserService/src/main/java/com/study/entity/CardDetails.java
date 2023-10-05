package com.study.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "card_datails")
public class CardDetails {
    @Id
    private String cardId;
    private String name;
    private String cardNumber;
    private Integer validUntilMonth;
    private Integer validUntilYear;
    private Integer cvv;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;
}
