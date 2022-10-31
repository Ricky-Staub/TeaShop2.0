package com.example.jwt.domain.entitys.role;

import com.example.jwt.core.generic.ExtendedRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends ExtendedRepository<Role> {

    Optional<Role> findByName(String name);
}