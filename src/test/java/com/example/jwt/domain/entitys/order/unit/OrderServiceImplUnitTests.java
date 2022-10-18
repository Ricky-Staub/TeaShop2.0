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
import com.example.jwt.domain.entitys.user.UserDetailsImpl;
import com.example.jwt.domain.entitys.user.UserServiceImpl;
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
    private OrderServiceImpl orderServiceImpl;

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private OrderRepository orderRepository;

    private Order dummyOrder;

    private User dummyUser;

    private Tea dummyTea;

    private List<Order> dummyOrders;

    @BeforeEach
    public void setUp() {
        Authority dummyAuthority = new Authority(UUID.randomUUID(), "name");
        Role dummyRole = new Role(UUID.randomUUID(), "USER_SEE", Set.of(dummyAuthority));
        Rank dummyRank = new Rank(UUID.randomUUID(), "title", 55, 7);
        dummyUser = new User(UUID.randomUUID(), 33, "sdfg", "sdfgh", 18, "sdf@jk.ch", "12345", false, dummyRank, Set.of(dummyRole));
        TeaType dummyTeaType = new TeaType(UUID.randomUUID(), "huso", 18, 0);
        dummyTea = new Tea(UUID.randomUUID(),  "description", 12, null, 5, dummyTeaType, null);


        dummyOrders = Stream.of(new Order(UUID.randomUUID(), 55, dummyUser, null), new Order(UUID.randomUUID(), 55, dummyUser, null)).collect(Collectors.toList());
        dummyOrder = dummyOrders.get(0);

        OrderPosition dummyOrderPosition = new OrderPosition(UUID.randomUUID(), 3, dummyOrder, dummyTea);
        dummyOrder.setOrderPositions(Set.of(dummyOrderPosition));
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
        assertThat(orderServiceImpl.findAll()).usingRecursiveComparison().isEqualTo(dummyOrders);
        verify(orderRepository, times(1)).findAll();
    }

//CreateOrder
//    @Test
//    public void Create_requestOrdernew_expectNewOrders() {
//        given(orderRepository.save(dummyOrder));
//        assertThat(orderServiceImpl.createOrder(dummyOrder)).isEqualTo(dummyOrder);
//        verify(orderRepository, times(1)).save(dummyOrder);
//    }

    //isAmountInStockKorrect
//    @Test
//    public void isAmountinStockcorrect_expectStockIsGood() {
//        given(orderRepository.save(dummyOrder));
//        assertThat(orderService.createOrder(dummyOrder)).isEqualTo(dummyOrder);
//        verify(orderRepository, times(1)).save(dummyOrder);
//    }
//

    //CalculateSeeds

    //UserOldEnaugh
    @Test
    public void userOldEnough_expectUserIsOldEnaught() {
        given(userServiceImpl.findCurrentUser()).willReturn(new UserDetailsImpl(dummyUser));
        assertThat(orderServiceImpl.isUserOldEnough(List.of(dummyTea))).isEqualTo(true);
        verify(orderRepository, times(1)).save(dummyOrder);

    }

    //rankCheck
//    @Test
//    public void rankcheck_expectRank() {
//        given(orderRepository.save(dummyOrder));
//        assertThat(orderService.isRankHightEnaught(dummyUser)).isEqualTo(dummyOrder);
//        verify(orderRepository, times(1)).save(dummyRank);
//    }



    //updateById
    //not working
    @Test
    public void UpdateById_requestOrdersAll_expectUpdateOrders() {
        given(orderRepository.findById(any(UUID.class))).will(invocation -> {
                    if ("non-existent".equals(invocation.getArgument(0)))
                        throw new NoSuchElementException("No such country present");
                    return Optional.of(dummyOrder);
        });

        System.out.println(dummyOrder.getId());
        System.out.println(dummyOrder.getPrice());
        assertThat(orderServiceImpl.updateById(dummyOrder.getId(), dummyOrder.setPrice(16.5F)));
        verify(orderRepository, times(1)).findById(any(UUID.class));
        System.out.println(dummyOrder.getPrice());

    }


}