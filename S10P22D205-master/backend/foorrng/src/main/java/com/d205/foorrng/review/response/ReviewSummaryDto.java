package com.d205.foorrng.review.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSummaryDto {
    private Long totalReviews;
    private Long rvIsDelicious;
    private Long isCool;
    private Long isClean;
    private Long isKind;
    private Long rvIdSpecial;
    private Long rvIsCheap;
    private Long rvIsFast;
}

