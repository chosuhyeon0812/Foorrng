package com.d205.foorrng.operationInfo.repository;

import com.d205.foorrng.operationInfo.QOperationInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class OperationInfoRepositoryImpl implements OperationInfoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public OperationInfoRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Set<String> findAllDaysByFoodTruckId(Long foodTruckId) {
        QOperationInfo operationInfo = QOperationInfo.operationInfo;
        return new HashSet<>(queryFactory.select(operationInfo.day)
                .from(operationInfo)
                .where(operationInfo.mark.foodtrucks.id.eq(foodTruckId))
                .fetch());
    }
}
