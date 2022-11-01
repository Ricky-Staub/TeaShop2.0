package com.example.TeaShop2.domain.entitys.order;

import com.example.TeaShop2.domain.entitys.country.Country;
import com.example.TeaShop2.domain.entitys.country.dto.CountryDTO;
import com.example.TeaShop2.domain.entitys.order.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    private final OrderMapper2 orderMapper2;


    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper, OrderMapper2 orderMapper2) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.orderMapper2 = orderMapper2;
    }



    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {
        List<OrderDTO> orders = orderMapper.fromOrderToOrderDTO(orderService.findAll());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/page/{pageNo}/{pageSize}")
    public ResponseEntity<List<OrderCreateDTO>> getAllOrdersPage(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        List<OrderCreateDTO> list = orderService.getAllOrdersPage(pageNo, pageSize);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderCreateDTO> findById(@PathVariable("id") UUID id) {
        Order order = orderService.findById(id);
        return new ResponseEntity<>(orderMapper.toDTO(order),HttpStatus.OK);
    }

    @GetMapping("/findown")
    public List<OrderCreateDTO> findOwn() { return orderMapper.toDTOs(orderService.findOwn());
    }

    @GetMapping("/teas")
    public List<OrderCountDTO> findTeas() { return orderService.findTeas();
    }

    // HIER SPEICHERST DU EINE NEUE BESTELLUNG
    @PostMapping
    public ResponseEntity<OrderCreateDTO> save(@RequestBody @Valid OrderCreateDTO orderCreateDTO) {
        Order order = orderService.createOrder(orderMapper.fromDTO(orderCreateDTO));
        return new ResponseEntity<>(orderMapper.toDTO(order), HttpStatus.CREATED);
    }

    //update für tests
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateById(@PathVariable UUID id, @Validated @RequestBody OrderDTO orderDTO) {
        Order order = orderService.updateById(id, orderMapper2.fromDTO(orderDTO));
        return new ResponseEntity<>(orderMapper2.toDTO(order), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}