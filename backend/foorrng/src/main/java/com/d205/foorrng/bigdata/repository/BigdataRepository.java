package com.d205.foorrng.bigdata.repository;

import com.d205.foorrng.bigdata.entity.Bigdata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BigdataRepository extends JpaRepository<Bigdata, Long> {
    List<Bigdata> findByFoodOrderByScoreDesc(String foodName);
}