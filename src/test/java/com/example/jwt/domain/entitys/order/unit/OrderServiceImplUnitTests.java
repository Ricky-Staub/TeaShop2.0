package com.example.jwt.domain.entitys.order.unit;



import com.example.jwt.domain.entitys.authority.Authority;
import com.example.jwt.domain.entitys.country.Country;
import com.example.jwt.domain.entitys.order.Order;
import com.example.jwt.domain.entitys.order.OrderRepository;
import com.example.jwt.domain.entitys.order.OrderServiceImpl;
import com.example.jwt.domain.entitys.ranking.Rank;
import com.example.jwt.domain.entitys.teas.Tea;
import com.example.jwt.domain.entitys.teatype.TeaType;
import com.example.jwt.domain.entitys.user.User;
import com.example.jwt.domain.orderposition.OrderPosition;
import com.example.jwt.domain.role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplUnitTests {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

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
        dummyRole = new Role(UUID.randomUUID(), "USER_SEE",Set.of(dummyAuthority));

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
//        given(orderRepository.findById(any(UUID.class))).will(invocation -> {
//            if ("non-existent".equals(invocation.getArgument(0)))
//                throw new NoSuchElementException("No such order present");
//            return Optional.of(dummyOrder);
//        });
//
//        assertThat(orderService.findById(dummyOrder.getId())).usingRecursiveComparison().isEqualTo(dummyOrder);
//
//        ArgumentCaptor<UUID> orderArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
//        verify(orderRepository, times(1)).findById(orderArgumentCaptor.capture());
//        assertThat(orderArgumentCaptor.getValue()).isEqualTo(dummyOrder.getId());
//    }



    @Test
    public void findAll_requestOrdersAll_expectOrders() {
        given(orderRepository.findAll()).willReturn(dummyOrders);
        assertThat(orderService.findAll()).usingRecursiveComparison().isEqualTo(dummyOrders);
        verify(orderRepository, times(1)).findAll();
    }

}
