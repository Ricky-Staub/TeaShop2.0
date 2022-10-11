package com.example.jwt.domain.entitys.authority;

import com.example.jwt.core.generic.ExtendedRepository;
import com.example.jwt.core.generic.ExtendedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

public class AuthorityServiceImpl extends ExtendedServiceImpl<Authority> implements AuthorityService{

    @Autowired
    public AuthorityServiceImpl(ExtendedRepository<Authority> repository, Logger logger) {
        super(repository);
    }
}
