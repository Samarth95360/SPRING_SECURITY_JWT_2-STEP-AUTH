package com.spring_security.JWT_Practice.AccountLockConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class UnlockAccountScheduler {

    private final UnlockAccountService unlockAccountService;

    @Autowired
    public UnlockAccountScheduler(UnlockAccountService unlockAccountService){
        this.unlockAccountService = unlockAccountService;
    }

    @Scheduled(fixedRate = 60000)
    public void unlockAccountPeriodically(){
        unlockAccountService.unlockAccountService();
    }

}
