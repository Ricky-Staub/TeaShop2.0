package com.example.jwt.domain.entitys.user;

import com.example.jwt.core.generic.ExtendedRepository;
import com.example.jwt.domain.entitys.order.dto.OrderCountDTO;
import com.example.jwt.domain.entitys.user.dto.UserBestDTO;
import com.example.jwt.domain.entitys.user.dto.UserDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends ExtendedRepository<User> {
    Optional<User> findByEmail(String email);

    @Query(value = "select sum(orders.price) as summe, email as mail, orders.created_at as created from orders join order_position op on orders.id = op.order_id join users u on u.id = orders.user_id where orders.created_at > current_date - interval '1 Month' group by price, email, orders.created_at order by price limit 1 ;\n", nativeQuery = true)
    Optional<List<UserBestDTO>> findMostOrders();
}
