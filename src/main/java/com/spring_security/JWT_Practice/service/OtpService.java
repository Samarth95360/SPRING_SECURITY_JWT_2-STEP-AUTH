package com.spring_security.JWT_Practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private final EmailService emailService;

    private final Map<String,String> otp = new HashMap<>();

    @Autowired
    public OtpService(EmailService emailService){
        this.emailService = emailService;
    }

    public void createOtp(String email){
        System.out.println("Email for otp is :- "+email);
        String data = String.valueOf(new Random().nextInt(999999));
        otp.put(email,data);
        emailService.senMail(email,"Otp for Secure Login","Hi there user your OTP is :- "+data+"\n and is valid for 2 min.\n Thank You");
    }

    public boolean verifyOtp(String email,String otpToVerify){

        String value = otp.get(email);
        if(value != null && value.equals(otpToVerify)){
            otp.remove(email);
            return true;
        }
        return false;
    }

}
