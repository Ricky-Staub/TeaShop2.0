package com.example.jwt.domain.role;

import com.example.jwt.core.generic.ExtendedRepository;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface RoleRepository extends ExtendedRepository<Role> {

    Optional<Role> findByName(String name);

}
