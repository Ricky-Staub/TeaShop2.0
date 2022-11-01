package com.example.TeaShop2.domain.entitys.country.dto;

import com.example.TeaShop2.domain.entitys.country.Country;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-01T13:37:47+0100",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class CountryMapperImpl implements CountryMapper {

    @Override
    public CountryDTO countryToCountryDTOWithoutID(Country country) {
        if ( country == null ) {
            return null;
        }

        CountryDTO countryDTO = new CountryDTO();

        countryDTO.setId( country.getId() );
        countryDTO.setCountry( country.getCountry() );

        return countryDTO;
    }

    @Override
    public Object fromDTO(CountryDTO countryDTO) {
        if ( countryDTO == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public Object toDTO(Country country) {
        if ( country == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }
}
