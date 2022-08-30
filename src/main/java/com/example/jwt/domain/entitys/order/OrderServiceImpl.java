package com.example.jwt.domain.entitys.order;

import com.example.jwt.core.generic.ExtendedRepository;
import com.example.jwt.core.generic.ExtendedServiceImpl;
import com.example.jwt.domain.entitys.order.dto.OrderCountDTO;
import com.example.jwt.domain.entitys.ranking.Rank;
import com.example.jwt.domain.entitys.ranking.RankService;
import com.example.jwt.domain.entitys.teas.TeaService;
import com.example.jwt.domain.entitys.user.UserService;
import com.example.jwt.domain.orderposition.OrderPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ExtendedServiceImpl<Order> implements OrderService {

    private UserService userService;
    private TeaService teaService;
    private RankService rankService;




    @Autowired
    public OrderServiceImpl(ExtendedRepository<Order> repository, UserService userService, TeaService teaService, RankService rankService) {
        super(repository);
        this.userService = userService;
        this.teaService = teaService;
        this.rankService = rankService;


    }


    @Override
    @Transactional
    public Order createOrder(Order order) {
        Set<OrderPosition> detachedPositions = order.getOrderPositions();
        Order cachedOrdering = save(order.setOrderPositions(new HashSet<>()).setUser(userService.findCurrentUser().user()));
        cachedOrdering.setOrderPositions(detachedPositions.stream().map(p -> p.setOrder(cachedOrdering)).collect(Collectors.toSet()));
        Order order1 = save(cachedOrdering);
        order1.getUser().getRank().getReduction();
        order.setOrderPositions(order.getOrderPositions().stream().map(orderPosition -> {
            orderPosition.setTea(teaService.findById(orderPosition.getTea().getId()));
            return orderPosition.setOrder(order1);
        }).collect(Collectors.toSet()));

        Integer sum = order.getOrderPositions().stream().mapToInt(p -> (int) p.getTea().getPrice() * p.getAmount() /*p.getReduction()*/).sum();
        order1.setPrice(sum);
        float i = sum * userService.findCurrentUser().user().getRank().getReduction();
        order1.setPrice(i);
        float halved = order1.getPrice() / 2;
        order1.getUser().setSeeds((int) halved + order1.getUser().getSeeds());
        Rank rank = rankService.findRankBySeeds(order1.getUser().getSeeds());
        order1.getUser().setRank(rank);


        /*if (orderService.findTeas(teaService.findById('a47d9683-2aed-42a3-846f-ccea365c7c9d')) && userService.findCurrentUser().user().getAge() >= 18) {
            return save(order1);
        } else{
            System.out.println("fuck you.");
        }*/



        if (userService.findCurrentUser().user().getRank().getTitle() == "platinum") {
            return save(order1);

        }else if(userService.findCurrentUser().user().getRank().getTitle() == "diamond") {
            return save(order1);
        }else{
            System.out.println("fuck you.");
        }

        //return save(cachedOrdering);

        return cachedOrdering;
    }

    public List<Order> findOwn(){
        Optional<List<Order>> optional=((OrderRepository)super.getRepository()).findOwn(userService.findCurrentUser().user().getId());
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NoSuchElementException("No value present");
        }
    }

    public List<OrderCountDTO> findTeas(){
        Optional<List<OrderCountDTO>> optional=((OrderRepository)super.getRepository()).findTeas(userService.findCurrentUser().user().getId());
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NoSuchElementException("No value present");
        }
    }
}
