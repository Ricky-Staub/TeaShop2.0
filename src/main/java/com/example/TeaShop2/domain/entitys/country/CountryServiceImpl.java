package com.example.TeaShop2.domain.entitys.country;

import com.example.TeaShop2.core.generic.ExtendedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl extends ExtendedServiceImpl<Country> implements CountryService {

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository){
        super(countryRepository);
    }

    @Override
    public Object findByName(String name) {
        return null;
    }
}