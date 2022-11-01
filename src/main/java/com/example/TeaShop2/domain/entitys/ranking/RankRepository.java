package com.example.TeaShop2.domain.entitys.ranking;

import com.example.TeaShop2.core.generic.ExtendedRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RankRepository extends ExtendedRepository<Rank> {
    Optional<Rank> findByTitle(String title);
    @Query(value = "select * from rank where seeds <= :seeds order by seeds desc limit 1", nativeQuery = true)
    Rank findRankBySeeds(@Param("seeds") Integer seeds);
}