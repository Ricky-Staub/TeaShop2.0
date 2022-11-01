package com.example.TeaShop2.domain.entitys.order.integration;

import com.example.TeaShop2.core.security.config.AuthorizationSchemas;
import com.example.TeaShop2.core.security.config.JwtProperties;
import com.example.TeaShop2.domain.entitys.authority.Authority;
import com.example.TeaShop2.domain.entitys.authority.AuthorityRepository;
import com.example.TeaShop2.domain.entitys.country.Country;
import com.example.TeaShop2.domain.entitys.country.CountryRepository;
import com.example.TeaShop2.domain.entitys.order.Order;
import com.example.TeaShop2.domain.entitys.order.OrderRepository;
import com.example.TeaShop2.domain.entitys.order.dto.OrderMapper;
import com.example.TeaShop2.domain.entitys.ranking.Rank;
import com.example.TeaShop2.domain.entitys.ranking.RankRepository;
import com.example.TeaShop2.domain.entitys.role.Role;
import com.example.TeaShop2.domain.entitys.role.RoleRepository;
import com.example.TeaShop2.domain.entitys.teas.Tea;
import com.example.TeaShop2.domain.entitys.teas.TeaRepository;
import com.example.TeaShop2.domain.entitys.teatype.TeaType;
import com.example.TeaShop2.domain.entitys.teatype.TeaTypeRepository;
import com.example.TeaShop2.domain.entitys.user.User;
import com.example.TeaShop2.domain.entitys.user.UserRepository;
import com.example.TeaShop2.domain.orderposition.OrderPosition;
import com.example.TeaShop2.domain.orderposition.OrderPositionRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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
    private CountryRepository countryRepository;
    @Autowired
    private RankRepository rankRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeaTypeRepository teaTypeRepository;
    @Autowired
    private TeaRepository teaRepository;
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
    private Role dummyRole;
    private Rank dummyRank;
    private Tea dummyTea;
    private TeaType dummyTeaType;
    private Country dummyCountry;
    private User dummyUser;
    private Authority dummyAuthority;
    private OrderPosition dummyOrderPosition;
    private Order dummyOrder;
    private List<Order> dummyOrders;

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

    @BeforeEach
    public void setUp() {
        dummyAuthority = authorityRepository.save(new Authority(UUID.randomUUID(), "name"));
        dummyRole = roleRepository.save(new Role(UUID.randomUUID(), "USER_SEE", Set.of(dummyAuthority)));
        dummyUser = userRepository.save(new User(UUID.randomUUID(), 33, "sdfg", "sdfgh", 18, "sdf@jk.ch", "12345", false, dummyRank, Set.of(dummyRole)));
        dummyRank = rankRepository.save(new Rank(UUID.randomUUID(), "Gold", 30, 7F));
        dummyCountry = countryRepository.save(new Country(UUID.randomUUID(), "kettle"));
        dummyTeaType = teaTypeRepository.save(new TeaType(UUID.randomUUID(), "huso", 18, 0));
        dummyTea = teaRepository.save(new Tea(UUID.randomUUID(), null, 12, null, 9, dummyTeaType, dummyCountry));
        dummyOrderPosition = orderPositionRepository.save(new OrderPosition(UUID.randomUUID(), 3, null, dummyTea));
//        dummyOrder = orderRepository.save(new Order(UUID.randomUUID(), 55, dummyUser, Set.of(dummyOrderPosition)));
//        dummyOrderPosition = orderPositionRepository.save(dummyOrderPosition.setOrder(dummyOrder));
    }

    //TODO: fix this test
//    @Test
//    public void retrieveAll_requestAllOrders_expectAllOrdersAsDTOS() throws Exception {
//        Authority authority = authorityRepository.saveAndFlush(new Authority().setName("USER_SEE"));
//        Role role = roleRepository.saveAndFlush(new Role().setName("USER").setAuthorities(Set.of(authority)));
//        User user = userRepository.saveAndFlush(new User().setEmail("john@doe.com").setRoles(Set.of(role)));
//
//        List<Order> dummyOrders = orderRepository.saveAllAndFlush(Stream.of(new Order(UUID.randomUUID(), 21.5F, user, null), new Order(UUID.randomUUID(), 76.8F, user, null)).collect(Collectors.toList()));
//
//        mvc.perform(MockMvcRequestBuilders
//                        .get("/order")
//                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + generateToken(user.getId()))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(Matchers.containsInAnyOrder(dummyOrders.get(0).getId().toString(), dummyOrders.get(1).getId().toString())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price").value(Matchers.containsInAnyOrder((((float) dummyOrders.get(0).getPrice())), (float) dummyOrders.get(1).getPrice())));
////                .andExpect(MockMvcResultMatchers.jsonPath("$[*].user").value(Matchers.containsInAnyOrder(dummyOrders.get(0).getUser() , dummyOrders.get(1).getUser())));
//    }

    @Test
    public void retrieveById_requestByIdOrder_expectByIdOrderAsDTO() throws Exception {
        Authority authority = authorityRepository.saveAndFlush(new Authority().setName("USER_SEE"));
        Role role = roleRepository.saveAndFlush(new Role().setName("ROLE_TESTER").setAuthorities(Set.of(authority)));
        User user = userRepository.saveAndFlush(new User().setEmail("john@doe.com").setRoles(Set.of(role)));
        Order dummyOrder = orderRepository.saveAndFlush(new Order(UUID.randomUUID(), 15, user, null));

        mvc.perform(MockMvcRequestBuilders
                        .get("/order/{id}", dummyOrder.getId())
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + generateToken(user.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dummyOrder.getId().toString()));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.order").value(dummyOrder.getOrder()));
    }

    //TODO: fix this test
//    @Test
//    public void create_requestProductDTOToBeCreated_expectCreatedProductAsDTO() throws Exception {
//        Authority authority = authorityRepository.saveAndFlush(new Authority().setName("USER_SEE"));
//        Role role = roleRepository.saveAndFlush(new Role().setName("ROLE_TESTER").setAuthorities(Set.of(authority)));
//        User user = userRepository.saveAndFlush(new User().setEmail("john@doe.com").setRoles(Set.of(role)).setAge(90).setRank(dummyRank));
//        //   User user1 = user.setRank(dummyRank);
//
//        List<Order> dummyOrders = orderRepository.saveAllAndFlush(Stream.of(new Order(UUID.randomUUID(), 20, user, new HashSet<>()), new Order(UUID.randomUUID(), 12, user, Set.of(dummyOrderPosition))).collect(Collectors.toList()));
//        dummyOrders.get(0).setId(null);
//        dummyOrders.get(1).setId(null);
//
//        mvc.perform(MockMvcRequestBuilders
//                        .post("/order")
//                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + generateToken(user.getId()))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(orderMapper.toDTO(dummyOrders.get(0)))))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dummyOrders.get(0).getId().toString()));
////                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id").value(dummyOrder().getId().toString()));
////                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(Matchers.containsInAnyOrder(dummyOrders.get(0).getId().toString(), dummyOrders.get(1).getId().toString())));
//    }
}