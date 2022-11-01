package com.example.TeaShop2.domain.entitys.country.dto;

import com.example.TeaShop2.core.generic.ExtendedDTO;

public class CountryDTO extends ExtendedDTO {

    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
