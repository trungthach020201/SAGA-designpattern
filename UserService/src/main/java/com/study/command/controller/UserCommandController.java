package com.study.command.controller;

import com.study.command.commands.CreateUserCommand;
import com.study.command.models.CreateUerModel;
import com.study.entity.User;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserCommandController {

    private final CommandGateway commandGateway;

    @PostMapping("/create")
    public ResponseEntity createNewUser(CreateUerModel createUerModel){
        CreateUserCommand createUserCommand = CreateUserCommand.builder()
                .userId(UUID.randomUUID().toString())
                .userName(createUerModel.getUserName())
                .email(createUerModel.getEmail())
                .password(createUerModel.getPassword())
                .firstName(createUerModel.getFirstName())
                .lastName(createUerModel.getLastName())
                .birthday(createUerModel.getBirthday())
                .cardNumber(createUerModel.getCardNumber())
                .isActive(false)
                .build();
        return ResponseEntity.ok(commandGateway.sendAndWait(createUserCommand));
    }

}
