package com.spring_security.JWT_Practice.GlobalExceptionHandler;

import com.spring_security.JWT_Practice.DTO.response.ErrorResponseDTO;
import com.spring_security.JWT_Practice.customException.OtpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.security.auth.login.AccountLockedException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccountLockedException(AccountLockedException ex, WebRequest request){
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OtpException.class)
    public ResponseEntity<ErrorResponseDTO> handleOtpException(OtpException ex,WebRequest request){
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
    }

}
