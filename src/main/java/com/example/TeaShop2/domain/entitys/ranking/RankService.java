package com.example.TeaShop2.domain.entitys.ranking;

import com.example.TeaShop2.core.generic.ExtendedService;


public interface RankService extends ExtendedService<Rank> {
    Rank findRankBySeeds(Integer seeds);

    Rank findByTitle(String title);
}