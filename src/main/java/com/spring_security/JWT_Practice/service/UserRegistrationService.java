package com.spring_security.JWT_Practice.service;

import com.spring_security.JWT_Practice.DTO.request.RegisterDTO;
import com.spring_security.JWT_Practice.DTO.response.RegistrationResponseDto;
import com.spring_security.JWT_Practice.Model.User;
import com.spring_security.JWT_Practice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserRegistrationService {

    private final UserRepo userRepo;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationService(UserRepo userRepo,BCryptPasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<RegistrationResponseDto> registerUser(RegisterDTO register){

        User user = userRepo.findByEmail(register.getEmail());

        if(user != null){
            RegistrationResponseDto responseDto = new RegistrationResponseDto();
            responseDto.setRegistered(false);
            responseDto.setMessage("User already Exist");
            responseDto.setDateTime(LocalDateTime.now());
            responseDto.setStatusCode(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(responseDto,HttpStatus.BAD_REQUEST);
        }

        User newUserToRegister = new User(
                register.getFullName(),
                register.getEmail(),
                passwordEncoder.encode(register.getPassword()),
                register.getRole()
        );

        userRepo.save(newUserToRegister);

        RegistrationResponseDto responseDto = new RegistrationResponseDto();
        responseDto.setRegistered(true);
        responseDto.setMessage("User Created Successfully");
        responseDto.setDateTime(LocalDateTime.now());
        responseDto.setStatusCode(HttpStatus.CREATED);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);

    }

}
