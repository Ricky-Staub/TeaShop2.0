package com.example.TeaShop2.domain.entitys.teas.dto;

import com.example.TeaShop2.core.generic.ExtendedDTO;
import com.example.TeaShop2.domain.entitys.country.dto.CountryDTO;
import com.example.TeaShop2.domain.entitys.teatype.dto.TeaTypeDTO;

import java.util.Date;

public class TeaDTO extends ExtendedDTO {

    private String description;

    private float price;

    private Date harvestDate;

    private Integer stock;

    private TeaTypeDTO teaType;

    private CountryDTO country;

    public TeaDTO(){

    }

    public String getDescription() {
        return description;
    }

    public TeaDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public TeaDTO setPrice(float price) {
        this.price = price;
        return this;
    }

    public Date getHarvestDate() {
        return harvestDate;
    }

    public TeaDTO setHarvestDate(Date harvestDate) {
        this.harvestDate = harvestDate;
        return this;
    }

    public Integer getStock() {
        return stock;
    }

    public TeaDTO setStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public TeaTypeDTO getTeaType() {
        return teaType;
    }

    public TeaDTO setTeaType(TeaTypeDTO teaType) {
        this.teaType = teaType;
        return this;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public TeaDTO setCountry(CountryDTO country) {
        this.country = country;
        return this;
    }
}
