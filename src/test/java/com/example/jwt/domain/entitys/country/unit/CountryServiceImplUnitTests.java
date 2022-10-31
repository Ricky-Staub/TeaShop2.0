package com.example.jwt.domain.entitys.country.unit;

import com.example.jwt.domain.entitys.country.Country;
import com.example.jwt.domain.entitys.country.CountryRepository;
import com.example.jwt.domain.entitys.country.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CountryServiceImplUnitTests {

    @InjectMocks
    private CountryServiceImpl countryService;

    @Mock
    private CountryRepository countryRepository;

    private Country dummyCountry;
    private List<Country> dummyCountrys;

    @BeforeEach
    public void setUp() {
        dummyCountry = new Country(UUID.randomUUID(), "kettle");
        dummyCountrys = Stream.of(new Country(UUID.randomUUID(), "shirt"), new Country(UUID.randomUUID(), "sandwich")).collect(Collectors.toList());
    }

    @Test
    public void findById_requestCountryById_expectCountry() {
        given(countryRepository.findById(any(UUID.class))).will(invocation -> {
            if ("non-existent".equals(invocation.getArgument(0)))
                throw new NoSuchElementException("No such country present");
            return Optional.of(dummyCountry);
        });

        assertThat(countryService.findById(dummyCountry.getId())).usingRecursiveComparison().isEqualTo(dummyCountry);

        ArgumentCaptor<UUID> countryArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(countryRepository, times(1)).findById(countryArgumentCaptor.capture());
        assertThat(countryArgumentCaptor.getValue()).isEqualTo(dummyCountry.getId());
    }


    @Test
    public void findAll_requestCountrysAll_expectCountrys() {
        given(countryRepository.findAll()).willReturn(dummyCountrys);

        assertThat(countryService.findAll()).usingRecursiveComparison().isEqualTo(dummyCountrys);

        verify(countryRepository, times(1)).findAll();
    }

    @Test
    public void deleteById_requestCountryById_expectdeletedCountry() {
        doNothing().when(countryRepository).deleteById(any(UUID.class));
        given(countryRepository.existsById(any(UUID.class))).willReturn(true);

        assertThat(countryService.deleteById(dummyCountry.getId())).isNull();

        ArgumentCaptor<UUID> countryArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(countryRepository, times(1)).deleteById(countryArgumentCaptor.capture());
    }
}