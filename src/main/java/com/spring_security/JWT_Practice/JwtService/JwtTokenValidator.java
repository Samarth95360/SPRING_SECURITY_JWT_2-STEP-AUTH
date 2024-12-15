package com.spring_security.JWT_Practice.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
//        System.out.println("Path is :- "+path);

        if(path.equals("/auth/login") || path.equals("/auth/register")){
            filterChain.doFilter(request,response);
            return;
        }

        String jwt = request.getHeader(JwtConst.JWT_HEADER);

        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);

            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConst.SECURITY_KEY.getBytes());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                // Check expiration
                if (claims.getExpiration().before(new Date())) {
                    throw new ExpiredJwtException(null, claims, "JWT Token is expired");
                }

                String email = String.valueOf(claims.get("email"));
                String authority = String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> authorities =
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authority);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException ex) {
                throw new InsufficientAuthenticationException("JWT Token is expired");
            } catch (JwtException | IllegalArgumentException ex) {
                throw new InsufficientAuthenticationException("Invalid JWT Token");
            }
        } else if (jwt == null) {
            throw new InsufficientAuthenticationException("Missing JWT Token");
        }

        filterChain.doFilter(request, response);
    }
}
