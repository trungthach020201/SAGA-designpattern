package com.study.command.aggregate;

import com.study.command.commands.CreateUserCommand;
import com.study.command.events.CreatedUserEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Aggregate
public class UserAggreagte {
    @AggregateIdentifier
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthday;
    private boolean isActive;
    private String cardNumber;

    public UserAggreagte() {
    }

    @CommandHandler
    public UserAggreagte(CreateUserCommand createUserCommand) {
        CreatedUserEvent createdUserEvent = new CreatedUserEvent();
        BeanUtils.copyProperties(createUserCommand,createdUserEvent);
        AggregateLifecycle.apply(createdUserEvent);
    }

    @EventSourcingHandler
    public void on (CreatedUserEvent event){
        this.userId=event.getUserId();
        this.userName= event.getUserName();
        this.email= event.getEmail();
        this.password = event.getPassword();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.birthday = event.getBirthday();
        this.isActive = event.isActive();
        this.cardNumber = event.getCardNumber();
    }
}
