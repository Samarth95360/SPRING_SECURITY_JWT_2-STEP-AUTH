package com.spring_security.JWT_Practice.service;

import com.spring_security.JWT_Practice.DTO.request.LoginDTO;
import com.spring_security.JWT_Practice.DTO.response.LoginResponseDTO;
import com.spring_security.JWT_Practice.JwtService.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserLoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider provider;
    private final OtpService otpService;

    @Autowired
    public UserLoginService(AuthenticationManager authenticationManager,JwtProvider provider,OtpService otpService){
        this.authenticationManager = authenticationManager;
        this.provider = provider;
        this.otpService = otpService;
    }

    public ResponseEntity<LoginResponseDTO> loginUser(LoginDTO login){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword())
        );

        //No need it define it here bec no subsequest rest is there for user to be auth
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = provider.generateTempJwtToken(authentication);

        otpService.createOtp(authentication.getName());

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
