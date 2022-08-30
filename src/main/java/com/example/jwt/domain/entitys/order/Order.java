package com.example.jwt.domain.entitys.order;

import com.example.jwt.core.generic.ExtendedEntity;
import com.example.jwt.domain.entitys.user.User;
import com.example.jwt.domain.orderposition.OrderPosition;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order extends ExtendedEntity {
    @Column(name= "price")
    private float price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OrderPosition> orderPositions;

    public Order() {

    }

    public Order(UUID id, float price, User user, Set<OrderPosition> orderPositions) {
        super(id);
        this.price = price;
        this.user = user;
        this.orderPositions = orderPositions;
    }

    public float getPrice() {
        return price;
    }

    public Order setPrice(float price) {
        this.price = price;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Order setUser(User user) {
        this.user = user;
        return this;
    }

    public Set<OrderPosition> getOrderPositions() {
        return orderPositions;
    }

    public Order setOrderPositions(Set<OrderPosition> orderPositions) {
        this.orderPositions = orderPositions;
        return this;
    }
}
