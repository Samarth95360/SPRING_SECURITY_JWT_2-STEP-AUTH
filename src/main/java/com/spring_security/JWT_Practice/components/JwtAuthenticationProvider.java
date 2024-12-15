package com.spring_security.JWT_Practice.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_security.JWT_Practice.DTO.response.ErrorResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationProvider implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

//        ObjectMapper objectMapper = new ObjectMapper();
//        ErrorResponseDTO responseDTO = new ErrorResponseDTO();

        if(authException instanceof BadCredentialsException){

            response.getWriter().write(authException.getMessage());

        }else if(authException instanceof UsernameNotFoundException){

            response.getWriter().write(authException.getMessage());

        }else if(authException instanceof AccountExpiredException){

            response.getWriter().write(authException.getMessage());

        }else if(authException instanceof InsufficientAuthenticationException){

            response.getWriter().write(authException.getMessage());

        }else {
            response.getWriter().write("Authentication failed: " + authException.getMessage());
        }

//        responseDTO.setDateTime(LocalDateTime.now());
//        response.getWriter().write(objectMapper.writeValueAsString(responseDTO));

//        String errorMessage;
//
//        if (authException.getCause() instanceof ExpiredJwtException) {
//            errorMessage = "Token has expired";
//        } else if (authException.getMessage().contains("Missing JWT Token")) {
//            errorMessage = "Token is missing";
//        } else {
//            errorMessage = "Invalid token or authentication failed";
//        }

//        response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
    }
}

