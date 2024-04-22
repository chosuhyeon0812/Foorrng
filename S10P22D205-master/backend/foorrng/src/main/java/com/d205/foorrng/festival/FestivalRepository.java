package com.d205.foorrng.festival;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long>, FestivalRepositoryCustom {

    Optional<Festival> findByFestivalId(Long festivalId);
}
