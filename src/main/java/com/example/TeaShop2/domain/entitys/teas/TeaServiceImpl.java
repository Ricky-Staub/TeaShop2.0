package com.example.TeaShop2.domain.entitys.teas;

import com.example.TeaShop2.core.generic.ExtendedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeaServiceImpl extends ExtendedServiceImpl<Tea> implements TeaService {

    @Autowired
    public TeaServiceImpl(TeaRepository teaRepository){
        super(teaRepository);
    }

    @Override
    public Object findByName(String name) {
        return null;
    }
}