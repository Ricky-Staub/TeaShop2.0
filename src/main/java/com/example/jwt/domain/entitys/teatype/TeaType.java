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


    public TeaType() {

    }

    public TeaType(UUID id, String tea_type) {
        super(id);
        this.teatype = tea_type;
    }

    public String getTeatype() {
        return teatype;
    }

    public TeaType setTeatype(String teatype) {
        this.teatype = teatype;
        return this;
    }
}
