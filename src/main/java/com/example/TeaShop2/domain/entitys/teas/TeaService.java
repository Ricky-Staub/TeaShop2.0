package com.example.TeaShop2.domain.entitys.teas;

import com.example.TeaShop2.core.generic.ExtendedService;
import org.springframework.transaction.annotation.Transactional;


public interface TeaService extends ExtendedService<Tea> {

    @Transactional
    Object findByName(String name);
}