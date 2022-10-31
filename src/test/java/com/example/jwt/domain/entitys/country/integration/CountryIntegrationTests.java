package com.example.jwt.domain.entitys.country.integration;

import com.example.jwt.core.security.config.AuthorizationSchemas;
import com.example.jwt.core.security.config.JwtProperties;
import com.example.jwt.domain.entitys.authority.Authority;
import com.example.jwt.domain.entitys.authority.AuthorityRepository;
import com.example.jwt.domain.entitys.country.Country;
import com.example.jwt.domain.entitys.country.CountryRepository;
import com.example.jwt.domain.entitys.user.User;
import com.example.jwt.domain.entitys.user.UserRepository;
import com.example.jwt.domain.entitys.role.Role;
import com.example.jwt.domain.entitys.role.RoleRepository;
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
public class CountryIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

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

//    private Country dummyCountry;
//    private List<Country> dummyCountrys;

    @BeforeEach
    public void setUp() {
//        dummyCountry = new Country(UUID.randomUUID(), "kettle");
//        dummyCountrys = Stream.of(new Country(UUID.randomUUID(), "shirt"), new Country(UUID.randomUUID(), "sandwich")).collect(Collectors.toList());
    }

    @Test
    public void retrieveAll_requestAllCountrys_expectAllCountrysAsDTOS() throws Exception {
        Authority authority = authorityRepository.saveAndFlush(new Authority().setName("USER_SEE"));
        Role role = roleRepository.saveAndFlush(new Role().setName("ROLE_TESTER").setAuthorities(Set.of(authority)));
        User user = userRepository.saveAndFlush(new User().setEmail("john@doe.com").setRoles(Set.of(role)));
        List<Country> dummyCountrys = countryRepository.saveAllAndFlush(Stream.of(new Country(UUID.randomUUID(), "swiss"), new Country(UUID.randomUUID(), "germany")).collect(Collectors.toList()));

        mvc.perform(MockMvcRequestBuilders
                        .get("/country")
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + generateToken(user.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(Matchers.containsInAnyOrder(dummyCountrys.get(0).getId().toString(), dummyCountrys.get(1).getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].country").value(Matchers.containsInAnyOrder(dummyCountrys.get(0).getCountry(), dummyCountrys.get(1).getCountry())));
    }

    @Test
    public void retrieveById_requestByIdCountry_expectByIdCountryAsDTO() throws Exception {
        Authority authority = authorityRepository.saveAndFlush(new Authority().setName("USER_SEE"));
        Role role = roleRepository.saveAndFlush(new Role().setName("ROLE_TESTER").setAuthorities(Set.of(authority)));
        User user = userRepository.saveAndFlush(new User().setEmail("john@doe.com").setRoles(Set.of(role)));
        Country dummyCountry = countryRepository.saveAndFlush(new Country(UUID.randomUUID(), "swiss"));

        mvc.perform(MockMvcRequestBuilders
                        .get("/country/{id}", dummyCountry.getId())
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + generateToken(user.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dummyCountry.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$" +
                        ".country").value(dummyCountry.getCountry()));
    }

}
