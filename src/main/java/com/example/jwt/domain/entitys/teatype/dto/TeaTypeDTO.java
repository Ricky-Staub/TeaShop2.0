package com.example.jwt.domain.entitys.teatype.dto;

import com.example.jwt.core.generic.ExtendedDTO;

public class TeaTypeDTO extends ExtendedDTO {

    private String teaType;

    public String getTeaType() {
        return teaType;
    }

    public TeaTypeDTO setTeaType(String teaType) {
        this.teaType = teaType;
        return this;
    }
}
