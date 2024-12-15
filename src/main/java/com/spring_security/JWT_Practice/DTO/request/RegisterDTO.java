package com.spring_security.JWT_Practice.DTO.request;

import com.spring_security.JWT_Practice.CustomInterface.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDTO {

    private String fullName;
    private String email;
    @ValidPassword    //Custom password validator
    private String password;
    private String role;

}
