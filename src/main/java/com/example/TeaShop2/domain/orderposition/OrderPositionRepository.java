package com.example.TeaShop2.domain.orderposition;

import com.example.TeaShop2.core.generic.ExtendedRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderPositionRepository extends ExtendedRepository<OrderPosition> {
    Optional<OrderPosition> findById (UUID id);
}