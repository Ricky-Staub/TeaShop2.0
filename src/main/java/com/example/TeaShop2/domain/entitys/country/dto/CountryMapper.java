package com.example.TeaShop2.domain.entitys.country.dto;

import com.example.TeaShop2.domain.entitys.country.Country;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CountryMapper {

    CountryDTO countryToCountryDTOWithoutID(Country country);


    Object fromDTO(CountryDTO countryDTO);

    Object toDTO(Country country);
}
