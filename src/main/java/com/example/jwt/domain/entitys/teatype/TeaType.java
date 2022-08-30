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


    public TeaType() {

    }

    public TeaType(UUID id, String teatype, Integer minAge) {
        super(id);
        this.teatype = teatype;
        this.minAge = minAge;
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
}
