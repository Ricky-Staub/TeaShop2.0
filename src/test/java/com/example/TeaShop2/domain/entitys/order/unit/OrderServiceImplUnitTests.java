package com.example.TeaShop2.domain.entitys.order.unit;

import com.example.TeaShop2.domain.entitys.authority.Authority;
import com.example.TeaShop2.domain.entitys.order.Order;
import com.example.TeaShop2.domain.entitys.order.OrderRepository;
import com.example.TeaShop2.domain.entitys.order.OrderService;
import com.example.TeaShop2.domain.entitys.order.OrderServiceImpl;
import com.example.TeaShop2.domain.entitys.ranking.Rank;
import com.example.TeaShop2.domain.entitys.ranking.RankService;
import com.example.TeaShop2.domain.entitys.role.Role;
import com.example.TeaShop2.domain.entitys.teas.Tea;
import com.example.TeaShop2.domain.entitys.teas.TeaService;
import com.example.TeaShop2.domain.entitys.teatype.TeaType;
import com.example.TeaShop2.domain.entitys.user.User;
import com.example.TeaShop2.domain.entitys.user.UserDetailsImpl;
import com.example.TeaShop2.domain.entitys.user.UserServiceImpl;
import com.example.TeaShop2.domain.orderposition.OrderPosition;
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

    @Mock
    private TeaService teaService;

    @Mock
    private RankService rankService;

    private Authority dummyAuthority;

    private Role dummyRole;

    private Rank dummyRank;

    private User dummyUser;

    private List<Order> dummyOrders;

    private Order dummyOrder;

    private List<Tea> dummyTeas;

    private Tea dummyTea;

    private TeaType dummyTeaType;

    private OrderPosition dummyOrderPosition;

    @BeforeEach
    public void setUp() {
        dummyAuthority = new Authority(UUID.randomUUID(), "name");
        dummyRole = new Role(UUID.randomUUID(), "USER_SEE", Set.of(dummyAuthority));
        dummyRank = new Rank(UUID.randomUUID(), "title", 55, 7);
        dummyUser = new User(UUID.randomUUID(), 33, "sdfg", "sdfgh", 18, "sdf@jk.ch", "12345", false, dummyRank, Set.of(dummyRole));
        dummyTeaType = new TeaType(UUID.randomUUID(), "huso", 18, 0);
        dummyTeas = Stream.of(new Tea(UUID.randomUUID(), "description", 12, null, 5, dummyTeaType, null)).collect(Collectors.toList());
        dummyTea = dummyTeas.get(0);
        dummyOrders = Stream.of(new Order(UUID.randomUUID(), 55, dummyUser, null), new Order(UUID.randomUUID(), 55, dummyUser, null)).collect(Collectors.toList());
        dummyOrder = dummyOrders.get(0);
        dummyOrderPosition = new OrderPosition(UUID.randomUUID(), 3, dummyOrder, dummyTea);
        dummyOrder.setOrderPositions(Set.of(dummyOrderPosition));
    }

    @Test
    public void findAll_requestOrdersAll_expectOrders() {
        given(orderRepository.findAll()).willReturn(dummyOrders);

        assertThat(orderServiceImpl.findAll()).usingRecursiveComparison().isEqualTo(dummyOrders);

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void findById_requestOrderById_expectOrder() {
        given(orderRepository.findById(any(UUID.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0)))
                throw new NoSuchElementException("No such order present");
            return Optional.of(dummyOrder);
        });

        assertThat(orderServiceImpl.findById(dummyOrder.getId())).usingRecursiveComparison().isEqualTo(dummyOrder);

        ArgumentCaptor<UUID> orderArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(orderRepository, times(1)).findById(orderArgumentCaptor.capture());
        assertThat(orderArgumentCaptor.getValue()).isEqualTo(dummyOrder.getId());
    }

    @Test
    public void Create_requestOrdernew_expectNewOrders() {
        given(userServiceImpl.findCurrentUser()).willReturn(new UserDetailsImpl(dummyUser));
        given(rankService.findRankBySeeds(any())).willReturn(dummyRank);

        assertThat(orderServiceImpl.calculateSeedsAndRank(dummyOrder)).isEqualTo(dummyOrder);

        verify(userServiceImpl, times(1)).findCurrentUser();
    }

    @Test
    public void UpdateById_requestOrdersAll_expectUpdateOrders() {
        given(orderRepository.findById(any(UUID.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0)))
                throw new NoSuchElementException("No such country present");
            return Optional.of(dummyOrder);
        });

        assertThat(orderServiceImpl.updateById(dummyOrder.getId(), dummyOrder.setPrice(16.5F)));

        verify(orderRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void userOldEnough_expectUserIsOldEnaught() {
        given(userServiceImpl.findCurrentUser()).willReturn(new UserDetailsImpl(dummyUser));

        assertThat(orderServiceImpl.isUserOldEnough(List.of(dummyTea))).isEqualTo(true);
    }

    @Test
    public void isAmountinStockcorrect_expectStockIsGood() {
        given(teaService.findById(any(UUID.class))).willReturn(dummyTea);

        assertThat(orderServiceImpl.isAmountinStockcorrect(dummyOrder)).isEqualTo(dummyOrder);

        verify(teaService, times(1)).findById(dummyTea.getId());
    }

    @Test
    public void rankCheck_expectRank() {
        given(userServiceImpl.findCurrentUser()).willReturn(new UserDetailsImpl(dummyUser));

        assertThat(orderServiceImpl.isRankHightEnaught(List.of(dummyTea))).isEqualTo(true);

    }
}