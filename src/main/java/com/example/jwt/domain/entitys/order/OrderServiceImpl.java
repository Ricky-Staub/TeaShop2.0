package com.example.jwt.domain.entitys.order;

import com.example.jwt.core.generic.ExtendedRepository;
import com.example.jwt.core.generic.ExtendedServiceImpl;
import com.example.jwt.domain.entitys.order.dto.OrderCountDTO;
import com.example.jwt.domain.entitys.ranking.Rank;
import com.example.jwt.domain.entitys.ranking.RankService;
import com.example.jwt.domain.entitys.teas.Tea;
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
            if (order.getOrderPositions().stream().noneMatch(p -> p.getAmount()  > orderPosition.getTea().getStock())) {
                orderPosition.getTea().setStock(orderPosition.getTea().getStock() - orderPosition.getAmount());
            } else {
                throw new RuntimeException("Out of Stock Error");
            } return orderPosition.setOrder(order1);
        }).collect(Collectors.toSet()));

        Integer sum = order.getOrderPositions().stream().mapToInt(p -> (int) p.getTea().getPrice() * p.getAmount()).sum();
        order1.setPrice(sum);
        float i = sum * userService.findCurrentUser().user().getRank().getReduction();
        order1.setPrice(i);
        float halved = order1.getPrice() / 2;
        order1.getUser().setSeeds((int) halved + order1.getUser().getSeeds());
        Rank rank = rankService.findRankBySeeds(order1.getUser().getSeeds());
        order1.getUser().setRank(rank);

// age check
        List<Tea> teas = new ArrayList<>();
        for (OrderPosition orderPosition : order.getOrderPositions()){
            teas.add(orderPosition.getTea());
        }
        if (!isUserOldEnough(teas)){
            throw new RuntimeException("Age Error");
        }

// rank check

        List<Tea> teas2 = new ArrayList<>();
        for (OrderPosition orderPosition : order.getOrderPositions()){
            teas2.add(orderPosition.getTea());
        }
        if (!isRankHightEnaught(teas2)){
            throw new RuntimeException("Rank Error");
        }


////stock check
//
//        List<Tea> teas3 = new ArrayList<>();
//        for (OrderPosition orderPosition : order.getOrderPositions()){
//            teas3.add(orderPosition.getTea());
//        }
//
//        boolean stockIsEnaught = false;
//        for (Tea tea : teas3) {
//            if (order.getOrderPositions().stream().noneMatch(p -> p.getAmount()  > tea.getStock())) {
//                stockIsEnaught = true;
//            } else {
//                stockIsEnaught = false;
//                break;
//            }
//        }
//
//        if (!stockIsEnaught) {
//            throw new RuntimeException("Error NR3");
//        }



        //return if ok
        return cachedOrdering;
    }


    //other stuff
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

// age check
    public boolean isUserOldEnough(List<Tea> teas){
        boolean isIts18 = false;
        for (Tea tea : teas) {
            if (userService.findCurrentUser().user().getAge() >= tea.getTeaType().getMinAge()) {
                   isIts18 = true;
            } else {
                isIts18 = false;
                break;
            }
        }
       return isIts18;
    }


    // rank check

    public boolean isRankHightEnaught(List<Tea> teas2){
        boolean rankIsEnaught = false;
        for (Tea tea : teas2) {
            if (userService.findCurrentUser().user().getSeeds() >= tea.getTeaType().getMinSeeds()) {
                rankIsEnaught = true;
            } else {
                rankIsEnaught = false;
                break;
            }
        }
        return rankIsEnaught;
    }





}
