package com.spring_security.JWT_Practice.JwtService;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtProvider {

    private final SecretKey key = Keys.hmacShaKeyFor(JwtConst.SECURITY_KEY.getBytes());

    public String generateTempJwtToken(Authentication authentication){
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = populateRoles(authorities);

        Instant expirationDate;

        if(role.equals("ROLE_OTP")){
            expirationDate = Instant.now().plusSeconds(300);
        }else{
            expirationDate = Instant.now().plusSeconds(86400);
        }

        Instant now = Instant.now();
//        Instant expirationDate = now.plusSeconds(120);

        return Jwts.builder()
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expirationDate))
//                .claim("type","temp")
                .claim("email",authentication.getName())
                .claim("authorities",role)
                .signWith(key)
                .compact();
    }

    private String populateRoles(Collection<? extends GrantedAuthority> authorities) {

        Set<String> roles = new HashSet<>();

        for (GrantedAuthority auth: authorities) {
            roles.add(auth.getAuthority());
        }
        return String.join(",",roles);

    }

}
