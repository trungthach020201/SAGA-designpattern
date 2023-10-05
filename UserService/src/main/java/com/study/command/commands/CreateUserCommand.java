package com.study.command.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Builder
@Data
public class CreateUserCommand {

    @TargetAggregateIdentifier
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
