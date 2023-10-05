package com.study.command.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreateUerModel {
    private String userName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthday;
    private boolean isActive;
    private String cardNumber;
}
