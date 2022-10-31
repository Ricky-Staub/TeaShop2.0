package com.example.jwt.domain.entitys.order;

import com.example.jwt.core.generic.ExtendedRepository;
import com.example.jwt.core.generic.ExtendedServiceImpl;
import com.example.jwt.domain.entitys.order.dto.OrderCountDTO;
import com.example.jwt.domain.entitys.order.dto.OrderCreateDTO;
import com.example.jwt.domain.entitys.order.dto.OrderMapper;
import com.example.jwt.domain.entitys.ranking.Rank;
import com.example.jwt.domain.entitys.ranking.RankService;
import com.example.jwt.domain.entitys.teas.Tea;
import com.example.jwt.domain.entitys.teas.TeaService;
import com.example.jwt.domain.entitys.user.UserService;
import com.example.jwt.domain.orderposition.OrderPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ExtendedServiceImpl<Order> implements OrderService {

    private UserService userService;
    private TeaService teaService;
    private RankService rankService;
    private OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(ExtendedRepository<Order> repository, UserService userService, TeaService teaService, RankService rankService, OrderMapper orderMapper) {
        super(repository);
        this.userService = userService;
        this.teaService = teaService;
        this.rankService = rankService;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        //TEIL 1
        Set<OrderPosition> detachedPositions = order.getOrderPositions();
        Order cachedOrdering = save(order.setOrderPositions(new HashSet<>()).setUser(userService.findCurrentUser().user()));
        cachedOrdering.setOrderPositions(detachedPositions.stream().map(p -> p.setOrder(cachedOrdering)).collect(Collectors.toSet()));
        //Order order1 = save(cachedOrdering);
        isAmountinStockcorrect(cachedOrdering);
        save(cachedOrdering);
        calculateSeedsAndRank(cachedOrdering);
        save(cachedOrdering);

// age check
        userOldEnough(cachedOrdering);
        save(cachedOrdering);

// rank check

        rankcheck(cachedOrdering);
        save(cachedOrdering);

//return if ok
        return cachedOrdering;
    }


    public Order isAmountinStockcorrect(Order order) {
        //order.getUser().getRank().getReduction();
        order.setOrderPositions(order.getOrderPositions().stream().map(orderPosition -> {
            orderPosition.setTea(teaService.findById(orderPosition.getTea().getId()));
            if (order.getOrderPositions().stream().noneMatch(p -> p.getAmount() > orderPosition.getTea().getStock())) {
                orderPosition.getTea().setStock(orderPosition.getTea().getStock() - orderPosition.getAmount());
            } else {
                throw new RuntimeException("Out of Stock Error");
            }
            return orderPosition.setOrder(order);
        }).collect(Collectors.toSet()));
        return order;
    }

    public Order calculateSeedsAndRank(Order order) {
        Integer sum = order.getOrderPositions().stream().mapToInt(p -> (int) p.getTea().getPrice() * p.getAmount()).sum();
        order.setPrice(sum);
        float i = sum * userService.findCurrentUser().user().getRank().getReduction();
        order.setPrice(i);
        float halved = order.getPrice() / 2;
        order.getUser().setSeeds((int) halved + order.getUser().getSeeds());
        Rank rank = rankService.findRankBySeeds(order.getUser().getSeeds());
        order.getUser().setRank(rank);

        return order;
    }


    private Order userOldEnough(Order order) {
        List<Tea> teas = new ArrayList<>();
        for (OrderPosition orderPosition : order.getOrderPositions()) {
            teas.add(orderPosition.getTea());
        }
        if (!isUserOldEnough(teas)) {
            throw new RuntimeException("Age Error");
        }
        return order;
    }

    private Order rankcheck(Order order) {
        List<Tea> teas2 = new ArrayList<>();
        for (OrderPosition orderPosition : order.getOrderPositions()) {
            teas2.add(orderPosition.getTea());
        }
        if (!isRankHightEnaught(teas2)) {
            throw new RuntimeException("Rank Error");
        }
        return order;
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
            System.out.println(tea.getTeaType().getMinAge());
            System.out.println(userService.findCurrentUser().user());
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

    @Override
    public List<OrderCreateDTO> getAllOrdersPage(Integer pageNo, Integer pageSize){
        List<Order> order =  findAll(PageRequest.of(pageNo,pageSize));
        return orderMapper.toDTOs(order);
    }


    @Override
    public Order updateById(UUID id, Order entity) throws NoSuchElementException {
        Order order = findById(id);
        order.setPrice(entity.getPrice());
        return save(order);
    }
}