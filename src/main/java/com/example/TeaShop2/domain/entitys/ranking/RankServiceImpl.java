package com.example.TeaShop2.domain.entitys.ranking;

import com.example.TeaShop2.core.generic.ExtendedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankServiceImpl extends ExtendedServiceImpl<Rank> implements RankService {

    @Autowired
    public RankServiceImpl(RankRepository repository) {
        super(repository);
    }

    @Override
    public Rank findByTitle(String title) {
        return findOrThrow(((RankRepository)super.getRepository()).findByTitle(title));
    }
    @Override
    public Rank findRankBySeeds(Integer seeds) {
        return ((RankRepository) super.getRepository()).findRankBySeeds(seeds);
    }
}