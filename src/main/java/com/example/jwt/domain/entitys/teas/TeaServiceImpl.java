package com.example.jwt.domain.entitys.teas;

import com.example.jwt.core.generic.ExtendedServiceImpl;
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
