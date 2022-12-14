package com.example.TeaShop2.domain.orderposition;

import com.example.TeaShop2.core.generic.ExtendedRepository;
import com.example.TeaShop2.core.generic.ExtendedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderPositionServiceImpl extends ExtendedServiceImpl<OrderPosition> implements OrderPositionService{

    @Autowired
    protected OrderPositionServiceImpl(ExtendedRepository<OrderPosition> repository) {
        super(repository);
    }
}