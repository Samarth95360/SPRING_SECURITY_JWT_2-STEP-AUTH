package com.spring_security.JWT_Practice.CustomEvent;

import org.springframework.context.ApplicationEvent;

public class LoginSuccessEvent extends ApplicationEvent {

    private final String userEmail;

    public LoginSuccessEvent(Object source,String userEmail) {
        super(source);
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
