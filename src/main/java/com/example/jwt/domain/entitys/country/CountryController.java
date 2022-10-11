package com.example.jwt.domain.entitys.country;

import com.example.jwt.domain.entitys.country.dto.CountryDTO;
import com.example.jwt.domain.entitys.country.dto.CountryMapper;
import com.example.jwt.domain.entitys.order.Order;
import com.example.jwt.domain.entitys.order.dto.OrderCreateDTO;
import com.example.jwt.domain.entitys.user.User;
import com.example.jwt.domain.entitys.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

//    @PostMapping
//    public ResponseEntity<CountryDTO> save(@RequestBody @Valid CountryDTO countryCreateDTO) {
//        Country country = countryServiceImpl.createCountry(countryMapper.fromDTO(countryDTO));
//        return new ResponseEntity<>(countryMapper.toDTO(country), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<UserDTO> updateById(@PathVariable UUID id, @Validated @RequestBody UserDTO userDTO) {
//        Country country = countryServiceImpl.updateById(id, countryMapper.fromDTO(countryDTO));
//        return new ResponseEntity<>(countryMapper.toDTO(country), HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        countryServiceImpl.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}