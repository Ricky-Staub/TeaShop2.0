package com.example.jwt.domain.entitys.order.unit;

import com.example.jwt.core.security.config.AuthorizationSchemas;
import com.example.jwt.core.security.config.JwtProperties;
import com.example.jwt.domain.entitys.authority.Authority;
import com.example.jwt.domain.entitys.order.Order;
import com.example.jwt.domain.entitys.order.OrderService;
import com.example.jwt.domain.entitys.order.dto.OrderMapper;
import com.example.jwt.domain.entitys.ranking.Rank;
import com.example.jwt.domain.entitys.teas.Tea;
import com.example.jwt.domain.entitys.user.User;
import com.example.jwt.domain.entitys.user.UserService;
import com.example.jwt.domain.orderposition.OrderPosition;
import com.example.jwt.domain.role.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc()
public class OrderControllerUnitTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtProperties jwtProperties;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    private String dummyToken;
    private Order dummyOrder;

    private User dummyUser;

    private Tea dummyTea;

    private Rank dummyRank;

    private Role dummyRole;

    private Authority dummyAuthority;

    private OrderPosition dummyOrderPosition;
    private List<Order> dummyOrders;

    private String generateToken() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());

        return Jwts.builder()
                .setClaims(Map.of("sub", UUID.randomUUID()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationMillis()))
                .setIssuer(jwtProperties.getIssuer())
                .signWith(Keys.hmacShaKeyFor(keyBytes))
                .compact();
    }

    @BeforeEach
    public void setUp() {
        dummyToken = generateToken();
        dummyAuthority = new Authority(UUID.randomUUID(), "name");
        dummyRole = new Role(UUID.randomUUID(), "USER_SEE", Set.of(dummyAuthority));
        dummyRank = new Rank(UUID.randomUUID(), "title", 55, 7);
        dummyUser = new User(UUID.randomUUID(), 33, "sdfg", "sdfgh", 18, "sdf@jk.ch", "12345", false, dummyRank, Set.of(dummyRole));


        dummyOrder = new Order(UUID.randomUUID(), 55, dummyUser, null);
        dummyTea = new Tea(UUID.randomUUID(), "description", 12, null, 5, null, null);
        dummyOrderPosition = new OrderPosition(UUID.randomUUID(), 3, dummyOrder, dummyTea);
        dummyOrder.setOrderPositions(Set.of(dummyOrderPosition));
        dummyOrders = Stream.of(new Order(UUID.randomUUID(), 55, dummyUser, null), new Order(UUID.randomUUID(), 55, dummyUser, null)).collect(Collectors.toList());

    }

    @Test
    public void retrieveAll_requestAllOrders_expectAllOrdersAsDTOS() throws Exception {
        given(userService.findById(any(UUID.class))).willReturn(new User().setRoles(Set.of(new Role().setAuthorities(Set.of(new Authority().setName("USER_SEE"))))));
        given(orderService.findAll()).willReturn(dummyOrders);

        mvc.perform(MockMvcRequestBuilders
                        .get("/order")
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
//                .andExpect(MockMvcResultMatchers.jsonPath("$[*].order").value(Matchers.containsInAnyOrder(dummyOrders.get(0).getOrder(), dummyOrders.get(1).getOrder())));
        verify(orderService, times(1)).findAll();
    }

    @Test
    public void create_requestOrderDTOToBeCreated_expectCreatedOrderAsDTO() throws Exception {
        UUID uuid = UUID.randomUUID();

        given(userService.findById(any(UUID.class))).willReturn(new User());
        given(orderService.createOrder(any(Order.class))).willReturn(dummyOrder);

        System.out.println("---------->" + dummyOrder.getId());
        System.out.println(dummyOrder.getPrice());

        mvc.perform(MockMvcRequestBuilders
                        .post("/order")
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderMapper.toDTO(dummyOrders.get(0)))))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dummyOrder.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id").value(dummyOrder.getUser().getId().toString()));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(dummyOrder.getPrice()));

//        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
//        verify(orderServiceImpl, times(1)).save(orderArgumentCaptor.capture());
//        assertThat(orderArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(dummyOrder.setId(uuid));
    }

    //Still error
    @Test
    public void updateOrder_requestOrderDTOToBeUpdated_expectUpdatedOrderDTO() throws Exception {
        float updatedOrderPrice = (float) 6.9;

        given(userService.findById(any(UUID.class))).willReturn(new User());
        given(orderService.updateById(any(UUID.class), (any(Order.class)))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0))) throw new Exception("Order could not be updated");
            return ((Order) invocation.getArgument(1)).setPrice(updatedOrderPrice);
        });

        mvc.perform(MockMvcRequestBuilders
                        .put("/order/" + dummyOrder.getId().toString())
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .content(new ObjectMapper().writeValueAsString(dummyOrder))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dummyOrder.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(updatedOrderPrice));

//        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
//        verify(orderService, times(1)).save(orderArgumentCaptor.capture());
//        assertAll(
//                () -> assertThat(orderArgumentCaptor.getValue().getId()).isEqualTo(dummyOrder.getId()),
//                () -> assertThat(orderArgumentCaptor.getValue().getPrice()).isEqualTo(updatedOrderPrice)
//        );
    }


    @Test
    public void deleteById_requestCountryById_expectdeletedCountry() throws Exception {
        given(userService.deleteById(any(UUID.class)));
        given(orderService.deleteById(any(UUID.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0)))
                throw new NoSuchElementException("No such Country present");
            return dummyOrder;
        });

        mvc.perform(MockMvcRequestBuilders
                        .delete("/order/{id}", dummyOrder.getId())
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dummyCountry.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.order").value(dummyOrder.getOrderPositions()));

        ArgumentCaptor<UUID> countryArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(orderService, times(1)).deleteById(countryArgumentCaptor.capture());
        assertThat(countryArgumentCaptor.getValue()).isEqualTo(dummyOrder.getId());
    }

}
