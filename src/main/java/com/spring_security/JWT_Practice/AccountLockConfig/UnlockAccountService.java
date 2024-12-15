package com.spring_security.JWT_Practice.AccountLockConfig;

import com.spring_security.JWT_Practice.Model.User;
import com.spring_security.JWT_Practice.repo.UserRepo;
import com.spring_security.JWT_Practice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UnlockAccountService {

    private static final int LOCK_TIME_DURATION = 2;

    private final UserRepo userRepo;
    private final EmailService emailService;

    @Autowired
    public UnlockAccountService(UserRepo userRepo,EmailService emailService){
        this.userRepo = userRepo;
        this.emailService = emailService;
    }

    public void unlockAccountService(){
        List<User> list = userRepo.findAllByAccountLockedTrue();

        list.forEach(
                user -> {
                    if(user.getLocalDateTime().plusMinutes(LOCK_TIME_DURATION).isBefore(LocalDateTime.now())){
                        user.setAccountLocked(false);
                        user.setFailedLoginAttempt(0);
                        user.setLocalDateTime(null);
                        userRepo.save(user);
                        emailService.senMail(user.getEmail(),"Account UnLocked","Please Try Login again . Your Account is now UnLocked");
                    }
                }
        );

    }

}
