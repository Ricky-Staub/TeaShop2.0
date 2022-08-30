package com.example.jwt.domain.entitys.user;

import com.example.jwt.core.generic.ExtendedService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService, ExtendedService<User> {
    User findByEmail(String email);

    User register(User user);

    UserDetailsImpl findCurrentUser();
}
