package com.spring_security.JWT_Practice.AccountLockConfig;

import com.spring_security.JWT_Practice.Model.User;
import com.spring_security.JWT_Practice.repo.UserRepo;
import com.spring_security.JWT_Practice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountLockedException;
import java.time.LocalDateTime;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final UserRepo userRepo;
    private final EmailService emailService;

    @Autowired
    public AuthenticationFailureListener(UserRepo userRepo,EmailService emailService){
        this.userRepo = userRepo;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String email = (String) event.getAuthentication().getName();
//        System.out.println("Email :- "+email);
        User user = userRepo.findByEmail(email);

        if(user != null){

            user.setFailedLoginAttempt(user.getFailedLoginAttempt()+1);
            if(user.getFailedLoginAttempt() > 3){
                user.setAccountLocked(true);
                user.setLocalDateTime(LocalDateTime.now());
                userRepo.save(user);
                emailService.senMail(email,"Account Locked","Sorry your acc is being locked bec of multiple failed login attempt please login again after 2 min");
//                try {
//                    throw new AccountLockedException("Sorry your acc is being locked bec of multiple failed login attempt please login again after 2 min");
//                } catch (AccountLockedException e) {
//                    throw new RuntimeException(e);
//                }
                return;
            }
            userRepo.save(user);
        }

    }

}
