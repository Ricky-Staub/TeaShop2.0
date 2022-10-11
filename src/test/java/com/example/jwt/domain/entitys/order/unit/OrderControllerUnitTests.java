package com.example.jwt.domain.entitys.order.unit;

import com.example.jwt.core.security.config.AuthorizationSchemas;
import com.example.jwt.core.security.config.JwtProperties;
import com.example.jwt.domain.entitys.authority.Authority;
import com.example.jwt.domain.entitys.order.Order;
import com.example.jwt.domain.entitys.order.OrderServiceImpl;
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
    private OrderServiceImpl orderServiceImpl;

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
        given(orderServiceImpl.findAll()).willReturn(dummyOrders);

        mvc.perform(MockMvcRequestBuilders
                        .get("/order")
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
//                .andExpect(MockMvcResultMatchers.jsonPath("$[*].order").value(Matchers.containsInAnyOrder(dummyOrders.get(0).getOrder(), dummyOrders.get(1).getOrder())));
        verify(orderServiceImpl, times(1)).findAll();
    }

    @Test
    public void create_requestOrderDTOToBeCreated_expectCreatedOrderAsDTO() throws Exception {
        UUID uuid = UUID.randomUUID();

        given(userService.findById(any(UUID.class))).willReturn(new User());
        given(orderServiceImpl.save(any(Order.class))).willReturn(dummyOrder);

        mvc.perform(MockMvcRequestBuilders
                        .post("/order")
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderMapper.toDTO(dummyOrders.get(0))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(dummyOrder.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].user").value(dummyOrder.getUser()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price").doesNotExist());

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderServiceImpl, times(1)).save(orderArgumentCaptor.capture());
        assertThat(orderArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(dummyOrder.setId(uuid));
    }


}
