package com.example.TeaShop2.domain.entitys.order.unit;

import com.example.TeaShop2.domain.entitys.authority.Authority;
import com.example.TeaShop2.domain.entitys.authority.AuthorityRepository;
import com.example.TeaShop2.domain.entitys.order.Order;
import com.example.TeaShop2.domain.entitys.order.OrderRepository;
import com.example.TeaShop2.domain.entitys.user.User;
import com.example.TeaShop2.domain.entitys.user.UserRepository;
import com.example.TeaShop2.domain.entitys.role.Role;
import com.example.TeaShop2.domain.entitys.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
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
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    private Authority dummyAuthority;

    private Role dummyRole;

    private User dummyUser;

    private List<Order> dummyOrders;

    private Order dummyOrder;

    @BeforeEach
    public void setUp() {
        dummyAuthority = authorityRepository.saveAndFlush(new Authority(UUID.randomUUID(), "USER_SEE"));
        dummyRole = roleRepository.saveAndFlush(new Role(UUID.randomUUID(), "USER", Set.of(dummyAuthority)));
        dummyUser = userRepository.saveAndFlush(new User(UUID.randomUUID(), 33, "sdfg", "sdfgh", 18, "sdf@jk.ch", "12345", false, null, Set.of(dummyRole)));
        dummyOrders = orderRepository.saveAllAndFlush(Stream.of(new Order(UUID.randomUUID(), 43, dummyUser, null), new Order(UUID.randomUUID(), 84, dummyUser, null), new Order(UUID.randomUUID(), 15, dummyUser, null)).collect(Collectors.toList()));
        dummyOrder = dummyOrders.get(0);
    }

    @Test
    public void findAll_requestAllOrders_expectAllOrders() {
        assertThat(orderRepository.findAll()).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(dummyOrders);
    }

    @Test
    public void save_requestOrderToBeSaved_expectSavedOrder() {
        assertThat(orderRepository.save(dummyOrder).equals(dummyOrder));
    }

    @Test
    public void update_requestOrderToBeUpdated_expectUpdateOrder() {
        assertThat(orderRepository.findById(dummyOrder.getId()));
    }

    @Test
    public void delete_requestOrderToBeDeleted_expectDeleteOrder() {
        UUID id = dummyOrders.get(0).getId();
        orderRepository.deleteById(id);
        assertThat(orderRepository.findById(id)).isNotPresent();
    }
}