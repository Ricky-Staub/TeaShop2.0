package com.example.jwt.domain.entitys.ranking;

import com.example.jwt.core.generic.ExtendedService;
import com.example.jwt.domain.role.Role;

import java.util.Optional;

public interface RankService extends ExtendedService<Rank> {
    Rank findRankBySeeds(Integer seeds);

    Rank findByTitle(String title);
}
