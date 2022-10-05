package com.example.jwt.domain.entitys.country.dto;

import com.example.jwt.domain.entitys.country.Country;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CountryMapper {

    CountryDTO countryToCountryDTOWithoutID(Country country);


}
