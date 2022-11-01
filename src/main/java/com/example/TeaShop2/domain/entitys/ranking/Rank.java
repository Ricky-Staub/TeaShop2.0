package com.example.TeaShop2.domain.entitys.ranking;

import com.example.TeaShop2.core.generic.ExtendedEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="rank")
public class Rank extends ExtendedEntity {
    @Column(name="title", unique = true, nullable = false)
    private String title;

    @Column(name="seeds", nullable = false)
    private int seeds;

    @Column(name="reduction", nullable = false)
    private float reduction;

    public Rank() {

    }

    public Rank(UUID id, String title, int seeds, float reduction) {
        super(id);
        this.title = title;
        this.seeds = seeds;
        this.reduction = reduction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSeeds() {
        return seeds;
    }

    public void setSeeds(int seeds) {
        this.seeds = seeds;
    }

    public float getReduction() {
        return reduction;
    }

    public void setReduction(float reduction) {
        this.reduction = reduction;
    }
}