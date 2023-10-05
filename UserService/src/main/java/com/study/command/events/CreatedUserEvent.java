package com.study.command.events;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class CreatedUserEvent {
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthday;
    private boolean isActive;
    private String cardNumber;
}
