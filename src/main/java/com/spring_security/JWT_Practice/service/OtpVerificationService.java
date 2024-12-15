package com.spring_security.JWT_Practice.service;

import com.spring_security.JWT_Practice.CustomEvent.LoginSuccessEvent;
import com.spring_security.JWT_Practice.DTO.response.LoginResponseDTO;
import com.spring_security.JWT_Practice.JwtService.JwtProvider;
import com.spring_security.JWT_Practice.customException.OtpException;
import com.spring_security.JWT_Practice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OtpVerificationService {

    private final OtpService otpService;
    private final UserDetailImpl userDetailImpl;
    private final JwtProvider jwtProvider;
    private final EmailService emailService;
    private final UserRepo userRepo;
    private final ApplicationEventPublisher publisher;


    @Autowired
    public OtpVerificationService(OtpService otpService, UserDetailImpl userDetailImpl, JwtProvider jwtProvider, EmailService emailService, UserRepo userRepo, ApplicationEventPublisher publisher){
        this.otpService = otpService;
        this.userDetailImpl = userDetailImpl;
        this.jwtProvider = jwtProvider;
        this.emailService = emailService;
        this.userRepo = userRepo;
        this.publisher = publisher;
    }

    public ResponseEntity<LoginResponseDTO> verifyOtp(String otpToVerify) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = authentication.getName();
        System.out.println("user email :- "+userEmail);

        if(!otpService.verifyOtp(userEmail,otpToVerify)){
            throw new OtpException("Invalid Opt Try again");
        }
            UserDetails userDetails = userDetailImpl.loadUserByUsername(userEmail);

            Authentication authentication1 = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

            String jwt = jwtProvider.generateTempJwtToken(authentication1);

            emailService.senMail(userEmail,"Login Successful","U are now a authenticated user . And can access resources according to the provided role");

//            User user = userRepo.findByEmail(userDetails.getUsername());
//
//            user.setFailedLoginAttempt(0);
//            userRepo.save(user);

            //Custom Event creation and publishing
            publisher.publishEvent(new LoginSuccessEvent(this,userDetails.getUsername()));

            LoginResponseDTO responseDTO = new LoginResponseDTO();

            responseDTO.setDateTime(LocalDateTime.now());
            if(jwt != null) {
                responseDTO.setJwt(jwt);
                responseDTO.setMessage("Jwt created Success");
                responseDTO.setJwtTokenAllocated(true);
            }else{
                responseDTO.setJwt(null);
                responseDTO.setMessage("Jwt Creation Fail");
                responseDTO.setJwtTokenAllocated(false);
            }
            return jwt == null ? new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST) : new ResponseEntity<>(responseDTO,HttpStatus.CREATED);

    }

}
