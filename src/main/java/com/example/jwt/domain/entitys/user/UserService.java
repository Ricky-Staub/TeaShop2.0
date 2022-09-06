package com.example.jwt.domain.entitys.user;

import com.example.jwt.core.generic.ExtendedService;
import com.example.jwt.domain.entitys.user.dto.UserBestDTO;
import com.example.jwt.domain.entitys.user.dto.UserLostMoneyDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService, ExtendedService<User> {
    User findByEmail(String email);

    User register(User user);

    UserDetailsImpl findCurrentUser();

// User isAccountNonLocked(UUID id, User user);

    User lockUser(UUID id, User user);

    List<UserBestDTO> findMostOrders();

    List<UserLostMoneyDTO> findLostMoney();
}