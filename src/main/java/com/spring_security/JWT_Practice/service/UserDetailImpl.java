package com.spring_security.JWT_Practice.service;

import com.spring_security.JWT_Practice.Model.User;
import com.spring_security.JWT_Practice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserDetailImpl(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(userEmail);

        if(user == null){
            throw new UsernameNotFoundException("User not found with this id :- "+userEmail);
        }
        return new UserService(user);

    }
}
