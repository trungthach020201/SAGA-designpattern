package com.study.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_table")
public class User {
    @Id
    private String userId;
    private String email;
    private String password;
    private String userName;
    private String firstName;
    private String lastName;
    private Date birthday;
    private boolean isActive;
    @OneToMany(mappedBy="user")
    private Set<CardDetails> cardDetails;
}
