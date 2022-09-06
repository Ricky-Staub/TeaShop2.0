package com.example.jwt.domain.orderposition;

import com.example.jwt.core.generic.ExtendedRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderPositionRepository extends ExtendedRepository<OrderPosition> {
    Optional<OrderPosition> findById (UUID id);
}