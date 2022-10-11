package com.example.jwt.domain.entitys.order.unit;



import com.example.jwt.domain.entitys.authority.Authority;
import com.example.jwt.domain.entitys.order.Order;
import com.example.jwt.domain.entitys.order.OrderRepository;
import com.example.jwt.domain.entitys.ranking.Rank;
import com.example.jwt.domain.entitys.teas.Tea;
import com.example.jwt.domain.entitys.user.User;
import com.example.jwt.domain.orderposition.OrderPosition;
import com.example.jwt.domain.role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderRepositoryUnitTests {

    @Autowired
    OrderRepository orderRepository;
    private Order dummyOrder;

    private User dummyUser;

    private Tea dummyTea;

    private Rank dummyRank;

    private Role dummyRole;

    private Authority dummyAuthority;

    private OrderPosition dummyOrderPosition;
    private List<Order> dummyOrders;

    @BeforeEach
    public void setUp() {
        dummyAuthority = new Authority(UUID.randomUUID(), "name");
        dummyRole = new Role(UUID.randomUUID(), "USER_SEE", Set.of(dummyAuthority));

        dummyUser = new User(UUID.randomUUID(), 33, "sdfg", "sdfgh", 18, "sdf@jk.ch", "12345", false, dummyRank, Set.of(dummyRole));


        dummyRank = new Rank(UUID.randomUUID(), "title", 55, 7);
        dummyOrder = new Order(UUID.randomUUID(), 55, dummyUser, null);
        dummyTea = new Tea(UUID.randomUUID(),  "description", 12, null, 5, null, null);
        dummyOrderPosition = new OrderPosition(UUID.randomUUID(), 3, dummyOrder, dummyTea);
        dummyOrder.setOrderPositions(Set.of(dummyOrderPosition));
        dummyOrders = Stream.of(new Order(UUID.randomUUID(), 55, dummyUser, null), new Order(UUID.randomUUID(), 55, dummyUser, null)).collect(Collectors.toList());

    }


//    @Test
//    public void findById_requestOrderById_expectOrder() {
//        assertThat(orderRepository.findById(dummyOrders.get(0).getId())).hasValue(dummyOrders.get(1));
//    }

    @Test
    public void findAll_requestAllOrders_expectAllOrders() {
        assertThat(orderRepository.findAll()).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(dummyOrders);
    }

    @Test
    public void save_requestOrderToBeSaved_expectSavedOrder() {
        assertThat(orderRepository.save(dummyOrder));
    }

}
