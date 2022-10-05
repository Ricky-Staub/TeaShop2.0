package com.example.jwt.domain.entitys.country;

import com.example.jwt.domain.entitys.country.dto.CountryDTO;
import com.example.jwt.domain.entitys.country.dto.CountryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/country")
@RestController
public class CountryController {
private CountryServiceImpl countryServiceImpl;

private CountryMapper countryMapper;

    @Autowired
    public CountryController(CountryServiceImpl countryServiceImpl, CountryMapper countryMapper){
        this.countryServiceImpl = countryServiceImpl;
        this.countryMapper = countryMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> findBYId(@PathVariable("id") UUID id) {
        Country country = countryServiceImpl.findById(id);
        return new ResponseEntity<>(countryMapper.countryToCountryDTOWithoutID(country),HttpStatus.OK);
    }

    @GetMapping("")
    public  List<CountryDTO> findAll(){
        return countryServiceImpl.findAll().stream().map(country -> countryMapper.countryToCountryDTOWithoutID(country))
                .collect(Collectors.toList());
    }
}