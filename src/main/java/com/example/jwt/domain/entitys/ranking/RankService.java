package com.example.jwt.domain.entitys.ranking;

import com.example.jwt.core.generic.ExtendedService;


public interface RankService extends ExtendedService<Rank> {
    Rank findRankBySeeds(Integer seeds);

    Rank findByTitle(String title);
}