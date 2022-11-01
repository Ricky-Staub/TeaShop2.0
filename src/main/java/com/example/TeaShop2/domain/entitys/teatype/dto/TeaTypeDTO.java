package com.example.TeaShop2.domain.entitys.teatype.dto;

import com.example.TeaShop2.core.generic.ExtendedDTO;

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
