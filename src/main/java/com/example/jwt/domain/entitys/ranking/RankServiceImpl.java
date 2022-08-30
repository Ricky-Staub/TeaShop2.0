package com.example.jwt.domain.entitys.ranking;

import com.example.jwt.core.generic.ExtendedRepository;
import com.example.jwt.core.generic.ExtendedServiceImpl;
import com.example.jwt.domain.entitys.user.User;
import com.example.jwt.domain.role.Role;
import com.example.jwt.domain.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
