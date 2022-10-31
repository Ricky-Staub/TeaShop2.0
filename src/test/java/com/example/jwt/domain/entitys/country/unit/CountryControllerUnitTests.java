package com.example.jwt.domain.entitys.country.unit;

import com.example.jwt.core.security.config.AuthorizationSchemas;
import com.example.jwt.core.security.config.JwtProperties;
import com.example.jwt.domain.entitys.authority.Authority;
import com.example.jwt.domain.entitys.country.Country;
import com.example.jwt.domain.entitys.country.CountryServiceImpl;
import com.example.jwt.domain.entitys.role.Role;
import com.example.jwt.domain.entitys.user.User;
import com.example.jwt.domain.entitys.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class CountryControllerUnitTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtProperties jwtProperties;

    @MockBean
    private UserService userService;

    @MockBean
    private CountryServiceImpl countryServiceImpl;

    private String dummyToken;
    private Country dummyCountry;
    private List<Country> dummyCountrys;

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
        dummyCountrys = Stream.of(new Country(UUID.randomUUID(), "kettle"), new Country(UUID.randomUUID(), "swiss"), new Country(UUID.randomUUID(), "germany")).collect(Collectors.toList());
        dummyCountry = dummyCountrys.get(0);
    }

    @Test
    @DisplayName("GetAll")
    public void retrieveAll_requestAllCountries_expectAllCountriesAsDTOS() throws Exception {
        given(userService.findById(any(UUID.class))).willReturn(
                new User().setRoles(Set.of(new Role().setAuthorities(Set.of(new Authority().setName("USER_SEE"))))));
        given(countryServiceImpl.findAll()).willReturn(dummyCountrys);

        mvc.perform(MockMvcRequestBuilders
                        .get("/country")
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].country").value(Matchers.containsInAnyOrder(dummyCountrys.get(0).getCountry(), dummyCountrys.get(1).getCountry(), dummyCountrys.get(2).getCountry())));
        verify(countryServiceImpl, times(1)).findAll();
    }

    @Test
    public void retrieveById_requestCountryById_expectCountryAsDTO() throws Exception {
        given(userService.findById(any(UUID.class))).willReturn(new User());
        given(countryServiceImpl.findById(any(UUID.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0)))
                throw new NoSuchElementException("No such Country present");
            return dummyCountry;
        });

        mvc.perform(MockMvcRequestBuilders
                        .get("/country/{id}", dummyCountry.getId())
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.country").value(dummyCountry.getCountry()));

        ArgumentCaptor<UUID> countryArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(countryServiceImpl, times(1)).findById(countryArgumentCaptor.capture());
        assertThat(countryArgumentCaptor.getValue()).isEqualTo(dummyCountry.getId());
    }

    @Test
    public void deleteById_requestCountryById_expectdeletedCountry() throws Exception {
        given(userService.findById(any(UUID.class))).willReturn(new User().setRoles(Set.of(new Role().setAuthorities(Set.of(new Authority().setName("USER_SEE"))))));
        given(countryServiceImpl.deleteById(any(UUID.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0)))
                throw new NoSuchElementException("No such Country present");
            return null;
        });

        mvc.perform(MockMvcRequestBuilders
                        .delete("/country/{id}", dummyCountry.getId())
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        ArgumentCaptor<UUID> countryArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(countryServiceImpl, times(1)).deleteById(countryArgumentCaptor.capture());
        assertThat(countryArgumentCaptor.getValue()).isEqualTo(dummyCountry.getId());
    }
}