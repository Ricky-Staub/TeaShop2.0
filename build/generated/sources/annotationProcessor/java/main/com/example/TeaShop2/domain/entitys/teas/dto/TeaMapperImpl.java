package com.example.TeaShop2.domain.entitys.teas.dto;

import com.example.TeaShop2.domain.entitys.country.Country;
import com.example.TeaShop2.domain.entitys.country.dto.CountryDTO;
import com.example.TeaShop2.domain.entitys.teas.Tea;
import com.example.TeaShop2.domain.entitys.teatype.TeaType;
import com.example.TeaShop2.domain.entitys.teatype.dto.TeaTypeDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-01T13:37:47+0100",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class TeaMapperImpl implements TeaMapper {

    @Override
    public TeaDTO teaToTeaDTOWithoutID(Tea tea) {
        if ( tea == null ) {
            return null;
        }

        TeaDTO teaDTO = new TeaDTO();

        teaDTO.setId( tea.getId() );
        teaDTO.setDescription( tea.getDescription() );
        teaDTO.setPrice( tea.getPrice() );
        teaDTO.setHarvestDate( tea.getHarvestDate() );
        teaDTO.setStock( tea.getStock() );
        teaDTO.setTeaType( teaTypeToTeaTypeDTO( tea.getTeaType() ) );
        teaDTO.setCountry( countryToCountryDTO( tea.getCountry() ) );

        return teaDTO;
    }

    protected TeaTypeDTO teaTypeToTeaTypeDTO(TeaType teaType) {
        if ( teaType == null ) {
            return null;
        }

        TeaTypeDTO teaTypeDTO = new TeaTypeDTO();

        teaTypeDTO.setId( teaType.getId() );

        return teaTypeDTO;
    }

    protected CountryDTO countryToCountryDTO(Country country) {
        if ( country == null ) {
            return null;
        }

        CountryDTO countryDTO = new CountryDTO();

        countryDTO.setId( country.getId() );
        countryDTO.setCountry( country.getCountry() );

        return countryDTO;
    }
}
