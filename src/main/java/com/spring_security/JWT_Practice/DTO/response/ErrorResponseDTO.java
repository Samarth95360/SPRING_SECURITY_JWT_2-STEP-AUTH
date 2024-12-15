package com.spring_security.JWT_Practice.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponseDTO {

    private String message;
    private LocalDateTime dateTime = LocalDateTime.now();

}
