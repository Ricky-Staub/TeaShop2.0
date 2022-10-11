package com.example.jwt.domain.entitys.country.unit;


import com.example.jwt.domain.entitys.country.Country;
import com.example.jwt.domain.entitys.country.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CountryRepositoryUnitTests {

    @Autowired
    CountryRepository countryRepository;

    private List<Country> dummyCountrys;

    @BeforeEach
    public void setUp() {
        dummyCountrys = countryRepository.saveAll(Stream.of(new Country(UUID.randomUUID(), "swiss"), new Country(UUID.randomUUID(), "germany")).collect(Collectors.toList()));
    }

    @Test
    public void findById_requestCountryById_expectCountry() {
        assertThat(countryRepository.findById(dummyCountrys.get(0).getId())).hasValue(dummyCountrys.get(0));
    }

    @Test
    public void findAll_requestAllCountrys_expectAllCountrys() {
        assertThat(countryRepository.findAll()).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(dummyCountrys);
    }

    @Test
    public void deleteById_requestCountryById_expectdeletedCountry() {
       UUID id = dummyCountrys.get(0).getId();
        countryRepository.deleteById(id);

        assertThat(countryRepository.findById(id)).isNotPresent();
    }

}
