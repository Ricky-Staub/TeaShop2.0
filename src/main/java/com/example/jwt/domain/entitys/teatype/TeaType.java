package com.example.jwt.domain.entitys.teatype;

import com.example.jwt.core.generic.ExtendedEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="tea_type")
public class TeaType extends ExtendedEntity {

    @Column(name = "tea_type", unique = true, nullable = false)
    private String teatype;

    @Column(name = "minAge")
    private Integer minAge;

    @Column(name = "minSeeds")
    private Integer minSeeds;


    public TeaType() {

    }

    public TeaType(UUID id, String teatype, Integer minAge, Integer minSeeds) {
        super(id);
        this.teatype = teatype;
        this.minAge = minAge;
        this.minSeeds = minSeeds;
    }

    public String getTeatype() {
        return teatype;
    }

    public TeaType setTeatype(String teatype) {
        this.teatype = teatype;
        return this;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public TeaType setMinAge(Integer minAge) {
        this.minAge = minAge;
        return this;
    }

    public Integer getMinSeeds() {
        return minSeeds;
    }

    public TeaType setMinSeeds(Integer minSeeds) {
        this.minSeeds = minSeeds;
        return this;
    }
}
