package com.example.unit;

import com.example.jwt.core.security.config.AuthorizationSchemas;
import com.example.jwt.core.security.config.JwtProperties;
import com.example.jwt.domain.entitys.authority.Authority;
import com.example.jwt.domain.entitys.country.Country;
import com.example.jwt.domain.entitys.country.CountryService;
import com.example.jwt.domain.entitys.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.hamcrest.Matchers;
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
public class CountryControllerUnitTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtProperties jwtProperties;

    @MockBean
    private UserService userService;

    @MockBean
    private CountryService countryService;

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
        dummyCountry = new Country(UUID.randomUUID(), "kettle", 107);
        dummyCountrys = Stream.of(new Country(UUID.randomUUID(), "shirt", 49), new Country(UUID.randomUUID(), "sandwich", 8)).collect(Collectors.toList());
    }

    @Test
    /*
        @WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "userServiceImpl", value = "robert@gmail.com")
        Above annotation invokes the [userDetailsServiceBeanName].loadUserByUsername(String email) method with the parameter [value]. Hence, the method
        loadUserByUsername(String email) needs to be mocked accordingly. Even though the returned user is saved as principal in the security context, the
        result of the test method stays "FALSE: Status Expected:<200> but was:<403>". This is due to the fact that WebSecurityConfig.filterChain()
        gets invoked by mvc.perform() AFTER the annotation @WithUserDetails got triggered. This leads to a SecurityContextHolder.clearContext() by
        CustomAuthorizationFilter as no valid JWT was passed to doFilterInternal(). Following approach solves the given issue:
        -   Pass a dummy JWT to the requests triggered by mvc.perform()
        -   Mock the method UserService.findById and return the desired users with the requested roles and authorities
    */
    public void retrieveAll_requestAllCountrys_expectAllCountrysAsDTOS() throws Exception {
        given(userService.findById(any(UUID.class))).willReturn(
                new User().setRoles(Set.of(new Role().setAuthorities(Set.of(new Authority().setName("USER_READ"))))));
        given(countryService.findAll()).willReturn(dummyCountrys);

        mvc.perform(MockMvcRequestBuilders
                        .get("/countrys")
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(Matchers.containsInAnyOrder(dummyCountrys.get(0).getId().toString(), dummyCountrys.get(1).getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name").value(Matchers.containsInAnyOrder(dummyCountrys.get(0).getName(), dummyCountrys.get(1).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price").doesNotExist());

        verify(countryService, times(1)).findAll();
    }

    @Test
    public void retrieveById_requestCountryById_expectCountryAsDTO() throws Exception {
        given(userService.findById(any(UUID.class))).willReturn(new User());
        given(countryService.findById(any(UUID.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0)))
                throw new NoSuchElementException("No such Country present");
            return dummyCountry;
        });

        mvc.perform(MockMvcRequestBuilders
                        .get("/countrys/{id}", dummyCountry.getId())
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dummyCountry.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(dummyCountry.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").doesNotExist());

        ArgumentCaptor<UUID> countryArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(countryService, times(1)).findById(countryArgumentCaptor.capture());
        assertThat(countryArgumentCaptor.getValue()).isEqualTo(dummyCountry.getId());
    }

    @Test
    public void create_requestCountryDTOToBeCreated_expectCreatedCountryAsDTO() throws Exception {
        UUID uuid = UUID.randomUUID();

        given(userService.findById(any(UUID.class))).willReturn(new User());
        given(countryService.save(any(Country.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0))) throw new Exception("Country could not be created");
            return ((Country) invocation.getArgument(0)).setPrice(dummyCountry.getPrice()).setId(uuid);
        });

        mvc.perform(MockMvcRequestBuilders
                        .post("/countrys")
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .content(new ObjectMapper().writeValueAsString(dummyCountry.setId(null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(uuid.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(dummyCountry.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").doesNotExist());

        ArgumentCaptor<Country> countryArgumentCaptor = ArgumentCaptor.forClass(Country.class);
        verify(countryService, times(1)).save(countryArgumentCaptor.capture());
        assertThat(countryArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(dummyCountry.setId(uuid));
    }

    @Test
    public void updateCountry_requestCountryDTOToBeUpdated_expectUpdatedCountryDTO() throws Exception {
        String updatedCountryName = "updatedCountryName";

        given(userService.findById(any(UUID.class))).willReturn(new User());
        given(countryService.save(any(Country.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0))) throw new Exception("Country could not be updated");
            return ((Country) invocation.getArgument(0)).setName(updatedCountryName);
        });

        mvc.perform(MockMvcRequestBuilders
                        .put("/countrys")
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .content(new ObjectMapper().writeValueAsString(dummyCountry))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dummyCountry.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedCountryName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").doesNotExist());

        ArgumentCaptor<Country> countryArgumentCaptor = ArgumentCaptor.forClass(Country.class);
        verify(countryService, times(1)).save(countryArgumentCaptor.capture());
        assertAll(
                () -> assertThat(countryArgumentCaptor.getValue().getId()).isEqualTo(dummyCountry.getId()),
                () -> assertThat(countryArgumentCaptor.getValue().getName()).isEqualTo(updatedCountryName),
                () -> assertThat(countryArgumentCaptor.getValue().getPrice()).isNull()
        );
    }

    @Test
    public void deleteCountryById_requestDeletionOfCountryById_expectAppropriateState() throws Exception {
        given(userService.findById(any(UUID.class))).willReturn(new User());
        given(countryService.deleteById(any(UUID.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0)))
                throw new NoSuchElementException("No such country present");
            return null;
        });

        mvc.perform(MockMvcRequestBuilders
                        .delete("/countrys/{id}", dummyCountry.getId())
                        .header(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + dummyToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        ArgumentCaptor<UUID> countryArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(countryService, times(1)).deleteById(countryArgumentCaptor.capture());
        assertThat(countryArgumentCaptor.getValue()).isEqualTo(dummyCountry.getId());
    }
}
