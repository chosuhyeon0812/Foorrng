package com.d205.foorrng.review.repository;

import com.d205.foorrng.review.repository.ReviewRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import static com.d205.foorrng.review.entity.QReview.review;

public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    public ReviewRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long countDeliciousReviewsByFoodtruckId(Long foodtruckSeq){
        return jpaQueryFactory
                .select(review)
                .from(review)
                .where(review.foodtrucks.id.eq(foodtruckSeq)
                        .and(review.rvIsDelicious.isTrue()))
                .fetchCount();
    }

    @Override
    public Long countSpecialReviewsByFoodtruckId(Long foodtruckSeq){
        return jpaQueryFactory
                .select(review)
                .from(review)
                .where(review.foodtrucks.id.eq(foodtruckSeq)
                        .and(review.rvIsSpecial.isTrue()))
                .fetchCount();
    }
    @Override
    public Long countChipReviewsByFoodtruckId(Long foodtruckSeq){
        return jpaQueryFactory
                .select(review)
                .from(review)
                .where(review.foodtrucks.id.eq(foodtruckSeq)
                        .and(review.rvIsCheap.isTrue()))
                .fetchCount();
    }
    @Override
    public Long countFastReviewsByFoodtruckId(Long foodtruckSeq){
        return jpaQueryFactory
                .select(review)
                .from(review)
                .where(review.foodtrucks.id.eq(foodtruckSeq)
                        .and(review.rvIsFast.isTrue()))
                .fetchCount();
    }
    @Override
    public Long countCleanReviewsByFoodtruckId(Long foodtruckSeq){
        return jpaQueryFactory
                .select(review)
                .from(review)
                .where(review.foodtrucks.id.eq(foodtruckSeq)
                        .and(review.rvIsClean.isTrue()))
                .fetchCount();
    }
    @Override
    public Long countCoolReviewsByFoodtruckId(Long foodtruckSeq){
        return jpaQueryFactory
                .select(review)
                .from(review)
                .where(review.foodtrucks.id.eq(foodtruckSeq)
                        .and(review.rvIsCool.isTrue()))
                .fetchCount();
    }
    @Override
    public Long countKindReviewsByFoodtruckId(Long foodtruckSeq){
        return jpaQueryFactory
                .select(review)
                .from(review)
                .where(review.foodtrucks.id.eq(foodtruckSeq)
                        .and(review.rvIsKind.isTrue()))
                .fetchCount();
    }
}
