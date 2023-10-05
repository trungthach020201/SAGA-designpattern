package com.study.command.events;

import com.study.entity.User;
import com.study.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventHandler {

    private final UserRepository userRepository;

    @org.axonframework.eventhandling.EventHandler
    public void on (CreatedUserEvent event){
        User user = new User();
        BeanUtils.copyProperties(event,user);
        userRepository.save(user);
    }
}
