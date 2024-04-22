package com.d205.foorrng.review.response;

import com.d205.foorrng.review.entity.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewResDto {
    private Boolean rvIsDelicious;
    private Boolean rvIsCool;
    private Boolean rvIsClean;
    private Boolean rvIsKind;
    private Boolean rvIsSpecial;
    private Boolean rvIsCheap;
    private Boolean rvIsFast;
    private Long createdDate;
    private String username;
    private Long foodtrucks;

    public static ReviewResDto fromEntity (Review review){
        return new ReviewResDto(
                review.getRvIsDelicious(),
                review.getRvIsCool(),
                review.getRvIsClean(),
                review.getRvIsKind(),
                review.getRvIsSpecial(),
                review.getRvIsCheap(),
                review.getRvIsFast(),
                review.getCreatedDate(),
                review.getUser().getName(),
                review.getFoodtrucks().getId()
        );
    }
}
