package com.example.jwt.domain.entitys.order;

import com.example.jwt.core.generic.ExtendedRepository;
import com.example.jwt.domain.entitys.order.dto.OrderCountDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends ExtendedRepository<Order> {
    @Query (value = "Select * from orders where user_id = :userId", nativeQuery = true)
    Optional<List<Order>> findOwn(@Param("userId") UUID userId);

    @Query (value = "select tt.tea_type as name,count(t.id) as quantity from orders as o left join order_position as op on o.id = op.order_id left join tea as t on t.id = op.tea_id left join tea_type as tt on t.tea_type_id = tt.id where o.user_id = :userId GROUP BY tt.tea_type", nativeQuery = true)
    Optional<List<OrderCountDTO>> findTeas(@Param("userId") UUID userId);



}

