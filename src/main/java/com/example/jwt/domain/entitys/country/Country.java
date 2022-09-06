package com.example.jwt.domain.entitys.country;

import com.example.jwt.core.generic.ExtendedEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "country")
public class Country extends ExtendedEntity {

    @Column(name = "country", unique = true, nullable = false)
    private String country;

    public Country() {

    }

    public Country(UUID id, String country) {
        super(id);
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
