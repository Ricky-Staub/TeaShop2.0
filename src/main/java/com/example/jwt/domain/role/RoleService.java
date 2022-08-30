package com.example.jwt.domain.role;

import com.example.jwt.core.generic.ExtendedService;

public interface RoleService extends ExtendedService<Role>{
    Role findByName(String name);

}
