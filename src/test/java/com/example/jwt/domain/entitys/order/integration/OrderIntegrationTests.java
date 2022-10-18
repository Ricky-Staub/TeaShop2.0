package com.example.jwt.domain.entitys.order.integration;

import com.example.jwt.core.security.config.AuthorizationSchemas;
import com.example.jwt.core.security.config.JwtProperties;
import com.example.jwt.domain.entitys.authority.Authority;
import com.example.jwt.domain.entitys.authority.AuthorityRepository;
import com.example.jwt.domain.entitys.order.Order;
import com.example.jwt.domain.entitys.order.OrderRepository;
import com.example.jwt.domain.entitys.order.OrderService;
import com.example.jwt.domain.entitys.order.dto.OrderMapper;
import com.example.jwt.domain.entitys.ranking.Rank;
import com.example.jwt.domain.entitys.ranking.RankRepository;
import com.example.jwt.domain.entitys.teas.Tea;
import com.example.jwt.domain.entitys.teatype.TeaType;
import com.example.jwt.domain.entitys.user.User;
import com.example.jwt.domain.entitys.user.UserRepository;
import com.example.jwt.domain.orderposition.OrderPosition;
import com.example.jwt.domain.orderposition.OrderPositionRepository;
import com.example.jwt.domain.role.Role;
import com.example.jwt.domain.role.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderIntegrationTests {

    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RankRepository rankRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderPositionRepository orderPositionRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private OrderMapper orderMapper;

    private Rank dummyRank;
    private  OrderPosition dummyOrderPosition;

    private String generateToken(UUID subject) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());

        return Jwts.builder()
                .setClaims(Map.of("sub", subject))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationMillis()))
                .setIssuer(jwtProperties.getIssuer())
                .signWith(Keys.hmacShaKeyFor(keyBytes))
                .compact();
    }

//    private Order dummyOrder;
//    private List<Order> dummyOrders;

    @BeforeEach
    public void setUp() {
//        dummyOrder = new Order(UUID.randomUUID(), "kettle");
//        dummyOrders = Stream.of(new Order(UUID.randomUUID(), "shirt"), new Order(UUID.randomUUID(), "sandwich")).collect(Collectors.toList()
        dummyRank = rankRepository.save(  new Rank(UUID.randomUUID(),"Gold",30,7F));
        dummyOrderPosition = orderPositionRepository.save( new OrderPosition(UUID.randomUUID(), 3, null, new Tea(UUID.randomUUID(),null,420,null,7,new TeaType(UUID.randomUUID(),null,16,null),null)));

    }

    @Test
    public void retrieveAll_requestAllOrders_expectAllOrdersAsDTOS() throws Exception {
        Authority authority = authorityRepository.saveAndFlush(new Authority().setName("USER_SEE"));
        Role role = roleRepository.saveAndFlush(new Role().setName("ROLE_TESTER").setAuthorities(Set.of(authority)));
        User user = userRepository.saveAndFlush(new User().setEmail("john@doe.com").setRoles(Set.of(role)));
        List<Order> dummyOrders = orderRepository.saveAllAndFlush(Stream.of(new Order(UUID.randomUUID(), (float) 24.1, user, null), new Order(UUID.randomUUID(), (float) 621, user, null)).collect(Collectors.toList()));

        mvc.perform(MockMvcRequestBuilders
                        .get("/order")
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + generateToken(user.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(Matchers.containsInAnyOrder(dummyOrders.get(0).getId().toString(), dummyOrders.get(1).getId().toString())));
//                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price").value(Matchers.containsInAnyOrder(dummyOrders.get(0).getPrice() , dummyOrders.get(1).getPrice())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[*].user").value(Matchers.containsInAnyOrder(dummyOrders.get(0).getUser() , dummyOrders.get(1).getUser())));

    }

//    @Test
//    public void retrieveById_requestByIdOrder_expectByIdOrderAsDTO() throws Exception {
//        Authority authority = authorityRepository.saveAndFlush(new Authority().setName("USER_SEE"));
//        Role role = roleRepository.saveAndFlush(new Role().setName("ROLE_TESTER").setAuthorities(Set.of(authority)));
//        User user = userRepository.saveAndFlush(new User().setEmail("john@doe.com").setRoles(Set.of(role)));
//        Order dummyOrder = orderRepository.saveAndFlush(new Order(UUID.randomUUID(), 15, user, null));
//
//        mvc.perform(MockMvcRequestBuilders
//                        .get("/order/{id}", dummyOrder.getId())
//                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + generateToken(user.getId()))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dummyOrder.getId().toString()));
////                .andExpect(MockMvcResultMatchers.jsonPath("$.order").value(dummyOrder.getOrder()));
//    }


    @Test
    public void create_requestProductDTOToBeCreated_expectCreatedProductAsDTO() throws Exception {
        Authority authority = authorityRepository.saveAndFlush(new Authority().setName("USER_SEE"));
        Role role = roleRepository.saveAndFlush(new Role().setName("ROLE_TESTER").setAuthorities(Set.of(authority)));
        User user = userRepository.saveAndFlush(new User().setEmail("john@doe.com").setRoles(Set.of(role)).setAge(18).setRank(dummyRank));
     //   User user1 = user.setRank(dummyRank);

        userRepository.saveAndFlush(user);
        System.out.println(user.getAge());

        List<Order> dummyOrders = orderRepository.saveAllAndFlush(Stream.of(new Order(UUID.randomUUID(), 20, user,new HashSet<>()), new Order(UUID.randomUUID(), 12, user,Set.of(dummyOrderPosition))).collect(Collectors.toList()));
        dummyOrders.get(0).setId(null);
        dummyOrders.get(1).setId(null);


        mvc.perform(MockMvcRequestBuilders
                        .post("/order")
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + generateToken(user.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderMapper.toDTO(dummyOrders.get(0)))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dummyOrders.get(0).getId().toString()));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id").value(dummyOrder().getId().toString()));
//                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(Matchers.containsInAnyOrder(dummyOrders.get(0).getId().toString(), dummyOrders.get(1).getId().toString())));


    }
//    @Test
//    public void updateProduct_requestProductDTOToBeUpdated_expectUpdatedProductDTO() throws Exception {}


}
