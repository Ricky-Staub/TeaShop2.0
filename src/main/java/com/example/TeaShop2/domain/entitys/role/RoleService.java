package com.example.TeaShop2.domain.entitys.role;

import com.example.TeaShop2.core.generic.ExtendedService;

public interface RoleService extends ExtendedService<Role>{
    Role findByName(String name);
}