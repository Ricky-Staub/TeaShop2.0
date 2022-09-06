package com.example.jwt.domain.orderposition;

import com.example.jwt.domain.orderposition.dto.OnlyAmountDTO;
import com.example.jwt.domain.orderposition.dto.OrderPositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/ordering")
public class OrderPositionContoller{

    private OrderPositionService orderPositionService;
    private OrderPositionMapper orderPositionMapper;

    @Autowired
    public OrderPositionContoller(OrderPositionService orderPositionService, OrderPositionMapper orderPositionMapper) {
        this.orderPositionService = orderPositionService;
        this.orderPositionMapper = orderPositionMapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('PRODUCT_SEE')")
    public ResponseEntity<OrderPosition> save(@RequestBody OrderPosition orderPosition){
        return new ResponseEntity<OrderPosition>(orderPositionService.save(orderPosition), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('PRODUCT_SEE')")
    public List<OnlyAmountDTO> findAll(){
        return orderPositionService.findAll().stream().map(orderPosition -> orderPositionMapper.orderpositionToOnlyAmountDTO(orderPosition))
                .collect(Collectors.toList());
    }
}