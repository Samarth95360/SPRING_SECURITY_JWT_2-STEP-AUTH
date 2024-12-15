package com.spring_security.JWT_Practice.components;

import com.spring_security.JWT_Practice.service.UserDetailImpl;
import com.spring_security.JWT_Practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailImpl userDetail;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(@Lazy UserDetailImpl userDetail, BCryptPasswordEncoder passwordEncoder){
        this.userDetail = userDetail;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserService userDetails = (UserService) userDetail.loadUserByUsername(email);

        if (!userDetails.isAccountNonExpired()){
            System.out.println("in acc exp");
            throw new AccountExpiredException("Account Locked Please try after some time");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            System.out.println("In bad cred");
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getOtpAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
