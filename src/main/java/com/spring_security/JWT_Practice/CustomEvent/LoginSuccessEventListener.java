package com.spring_security.JWT_Practice.CustomEvent;

import com.spring_security.JWT_Practice.Model.User;
import com.spring_security.JWT_Practice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessEventListener {

    private final UserRepo userRepo;

    @Autowired
    public LoginSuccessEventListener(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @EventListener
    public void handleLoginSuccessEventListener(LoginSuccessEvent event){
        String userEmail = event.getUserEmail();

        User user = userRepo.findByEmail(userEmail);
        user.setFailedLoginAttempt(0);
        userRepo.save(user);

    }

}
