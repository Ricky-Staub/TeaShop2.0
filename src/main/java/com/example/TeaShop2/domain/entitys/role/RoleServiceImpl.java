package com.example.TeaShop2.domain.entitys.role;

import com.example.TeaShop2.core.generic.ExtendedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ExtendedServiceImpl<Role> implements RoleService {

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        super(repository);
    }

    @Override
    public Role findByName(String name) {
        return findOrThrow(((RoleRepository)super.getRepository()).findByName(name));
    }
}