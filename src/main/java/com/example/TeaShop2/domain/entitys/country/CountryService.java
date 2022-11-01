package com.example.TeaShop2.domain.entitys.country;

import com.example.TeaShop2.core.generic.ExtendedService;
import org.springframework.transaction.annotation.Transactional;

public interface CountryService extends ExtendedService<Country> {

    @Transactional
    Object findByName(String name);

}
