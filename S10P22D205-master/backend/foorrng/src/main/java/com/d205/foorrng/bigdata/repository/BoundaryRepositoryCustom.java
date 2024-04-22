package com.d205.foorrng.bigdata.repository;

import com.d205.foorrng.bigdata.entity.Boundary;

import java.util.List;
import java.util.Optional;

public interface BoundaryRepositoryCustom {


    List<Boundary> findAllByAreaName(String areaName);
}
