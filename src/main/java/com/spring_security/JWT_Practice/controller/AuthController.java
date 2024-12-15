package com.spring_security.JWT_Practice.controller;

import com.spring_security.JWT_Practice.DTO.request.LoginDTO;
import com.spring_security.JWT_Practice.DTO.request.RegisterDTO;
import com.spring_security.JWT_Practice.DTO.response.LoginResponseDTO;
import com.spring_security.JWT_Practice.DTO.response.RegistrationResponseDto;
import com.spring_security.JWT_Practice.service.OtpVerificationService;
import com.spring_security.JWT_Practice.service.UserLoginService;
import com.spring_security.JWT_Practice.service.UserRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRegistrationService userRegistrationService;
    private final UserLoginService userLoginService;
    private final OtpVerificationService otpVerificationService;


    @Autowired
    public AuthController(UserLoginService userLoginService,UserRegistrationService userRegistrationService,OtpVerificationService otpVerificationService){
        this.userLoginService = userLoginService;
        this.userRegistrationService = userRegistrationService;
        this.otpVerificationService = otpVerificationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> registerUser(@Valid @RequestBody RegisterDTO register){
        return userRegistrationService.registerUser(register);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginDTO loginDTO){
        return userLoginService.loginUser(loginDTO);
    }

    @PostMapping("/{otp-matcher}")
    public ResponseEntity<LoginResponseDTO> verifyOtp(@RequestParam String otp) throws Exception {
        System.out.println("Otp is :- "+otp);
        return otpVerificationService.verifyOtp(otp);
    }

}
