package com.d205.foorrng.review.repository;

public interface ReviewRepositoryCustom {
    Long countDeliciousReviewsByFoodtruckId(Long foodtruckSeq);
    Long countSpecialReviewsByFoodtruckId(Long foodtruckSeq);
    Long countChipReviewsByFoodtruckId(Long foodtruckSeq);
    Long countFastReviewsByFoodtruckId(Long foodtruckSeq);
    Long countCleanReviewsByFoodtruckId(Long foodtruckSeq);
    Long countCoolReviewsByFoodtruckId(Long foodtruckSeq);
    Long countKindReviewsByFoodtruckId(Long foodtruckSeq);
}
