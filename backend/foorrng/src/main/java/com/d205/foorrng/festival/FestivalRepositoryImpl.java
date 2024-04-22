package com.d205.foorrng.festival;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class FestivalRepositoryImpl implements FestivalRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public FestivalRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Festival> findAllByPeriod(String period) {
        QFestival festival = QFestival.festival;

        return queryFactory.select(festival)
                .from(festival)
                .where(festival.period.like(period.toString() + ".%")
                        .or(festival.period.like("%" + period.toString() + "월%"))
                        .or(festival.period.like("0" + period.toString() + ".%")))
                .fetch();


        //return new HashSet<>(queryFactory.select(operationInfo.day)
        //                .from(operationInfo)
        //                .where(operationInfo.mark.foodtrucks.id.eq(foodTruckId))
        //                .fetch());

    }
}
