package com.d205.foorrng.bigdata.repository;

import com.d205.foorrng.bigdata.entity.Boundary;
import com.d205.foorrng.bigdata.entity.QBoundary;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class BoundaryRepositoryImpl implements BoundaryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public BoundaryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public List<Boundary> findAllByAreaName(String areaName) {
        QBoundary boundary = QBoundary.boundary;

        return queryFactory.select(boundary)
                .from(boundary)
                .where(boundary.areaName.like(areaName))
                .fetch();
    }
}
