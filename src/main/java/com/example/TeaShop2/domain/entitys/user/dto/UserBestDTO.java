package com.example.TeaShop2.domain.entitys.user.dto;

import java.time.LocalDateTime;

public interface UserBestDTO {

    float getSumme();
    LocalDateTime getCreated();
    String getMail();
}