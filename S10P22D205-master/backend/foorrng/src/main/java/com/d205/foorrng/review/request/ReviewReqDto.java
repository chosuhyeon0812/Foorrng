package com.d205.foorrng.review.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReqDto {
    @NotNull
    private Boolean rvIsDelicious;
    @NotNull
    private Boolean rvIsCool;
    @NotNull
    private Boolean rvIsClean;
    @NotNull
    private Boolean rvIsKind;
    @NotNull
    private Boolean rvIsSpecial;
    @NotNull
    private Boolean rvIsCheap;
    @NotNull
    private Boolean rvIsFast;
}
