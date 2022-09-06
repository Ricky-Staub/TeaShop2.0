package com.example.jwt.domain.entitys.teas;

import com.example.jwt.core.generic.ExtendedService;
import org.springframework.transaction.annotation.Transactional;


public interface TeaService extends ExtendedService<Tea> {

    @Transactional
    Object findByName(String name);
}