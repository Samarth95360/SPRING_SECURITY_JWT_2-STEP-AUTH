package com.spring_security.JWT_Practice.repo;

import com.spring_security.JWT_Practice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User findByEmail(String email);

    List<User> findAllByAccountLockedTrue();
}
