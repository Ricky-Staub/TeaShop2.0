package com.example.jwt.domain.entitys.teas;

import com.example.jwt.core.generic.ExtendedEntity;
import com.example.jwt.domain.entitys.country.Country;
import com.example.jwt.domain.entitys.teatype.TeaType;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="tea")
public class Tea extends ExtendedEntity {

    @Column(name = "description", unique = true)
    private String description;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "harvest_date")
    private Date harvestDate;

    @Column(name = "stock")
    private Integer stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tea_type_id", referencedColumnName = "id", nullable = false)
    private TeaType teaType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country country;

    public Tea() {

    }

    public Tea(UUID id, String description, float price, Date harvestDate, Integer stock, TeaType teaType, Country country) {
        super(id);
        this.description = description;
        this.price = price;
        this.harvestDate = harvestDate;
        this.stock = stock;
        this.teaType = teaType;
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public Tea setDescription(String description) {
        this.description = description;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public Tea setPrice(float price) {
        this.price = price;
        return this;
    }

    public Date getHarvestDate() {
        return harvestDate;
    }

    public Tea setHarvestDate(Date harvestDate) {
        this.harvestDate = harvestDate;
        return this;
    }

    public Integer getStock() {
        return stock;
    }

    public Tea setStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public TeaType getTeaType() {
        return teaType;
    }

    public Tea setTeaType(TeaType teaType) {
        this.teaType = teaType;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public Tea setCountry(Country country) {
        this.country = country;
        return this;
    }
}