package com.example.jwt.domain.entitys.user;

import com.example.jwt.core.generic.ExtendedRepository;
import com.example.jwt.domain.entitys.user.dto.UserBestDTO;
import com.example.jwt.domain.entitys.user.dto.UserLostMoneyDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends ExtendedRepository<User> {
    Optional<User> findByEmail(String email);

    @Query(value = "select sum(orders.price) as summe, email as mail, orders.created_at as created from orders join order_position op on orders.id = op.order_id join users u on u.id = orders.user_id where orders.created_at > current_date - interval '1 Month' group by price, email, orders.created_at order by price limit 1 ;\n", nativeQuery = true)
    Optional<List<UserBestDTO>> findMostOrders();

    @Query(value = "select sum(orders.price / r.reduction - orders.price) as rabatt, email from users  join orders on orders.user_id = users.id join order_position op on orders.id = op.order_id join tea t on t.id = op.tea_id join rank r on r.id = users.rank_id\n" +
            " group by email, tea_id;" +
            "\n", nativeQuery = true)
    Optional<List<UserLostMoneyDTO>> findLostMoney();
}