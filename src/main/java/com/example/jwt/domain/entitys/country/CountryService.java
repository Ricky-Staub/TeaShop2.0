package com.example.jwt.domain.entitys.country;

import com.example.jwt.core.generic.ExtendedService;
import org.springframework.transaction.annotation.Transactional;

public interface CountryService extends ExtendedService<Country> {

    @Transactional
    Object findByName(String name);

}
