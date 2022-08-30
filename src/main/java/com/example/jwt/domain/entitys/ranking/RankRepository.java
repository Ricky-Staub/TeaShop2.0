package com.example.jwt.domain.entitys.ranking;

import com.example.jwt.core.generic.ExtendedRepository;
import com.example.jwt.domain.role.Role;
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
