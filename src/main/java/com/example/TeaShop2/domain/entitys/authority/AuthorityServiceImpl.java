package com.example.TeaShop2.domain.entitys.authority;

import com.example.TeaShop2.core.generic.ExtendedRepository;
import com.example.TeaShop2.core.generic.ExtendedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

public class AuthorityServiceImpl extends ExtendedServiceImpl<Authority> implements AuthorityService{

    @Autowired
    public AuthorityServiceImpl(ExtendedRepository<Authority> repository, Logger logger) {
        super(repository);
    }
}
