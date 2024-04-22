package com.d205.foorrng.review.service;

import com.d205.foorrng.review.request.ReviewReqDto;
import com.d205.foorrng.review.response.ReviewResDto;
import com.d205.foorrng.review.response.ReviewSummaryDto;

import java.util.List;
import java.util.Map;

public interface ReviewService {
    // 리뷰 생성
    ReviewResDto createReview(Long foodtrucks_seq, ReviewReqDto reviewReqDto);

    // 리뷰 조회
     ReviewSummaryDto getReviews(Long foodtrucks_seq);

     // 리뷰 조회 - 안드 요청
    List<Map<String,Object>> getReviewlist(Long foodtruckId);
}
