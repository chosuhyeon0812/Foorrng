package com.d205.foorrng.review.service;

import com.d205.foorrng.common.exception.ErrorCode;
import com.d205.foorrng.common.exception.Exceptions;
import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.foodtruck.repository.FoodtrucksRepository;
import com.d205.foorrng.review.entity.Review;
import com.d205.foorrng.review.repository.ReviewRepository;
import com.d205.foorrng.review.request.ReviewReqDto;
import com.d205.foorrng.review.response.ReviewResDto;
import com.d205.foorrng.review.response.ReviewSummaryDto;
import com.d205.foorrng.review.service.ReviewService;
import com.d205.foorrng.user.entity.User;
import com.d205.foorrng.user.repository.UserRepository;
import com.d205.foorrng.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final FoodtrucksRepository foodtrucksRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    public ReviewResDto createReview(Long foodtrucks_seq, ReviewReqDto reviewReqDto) {

        // 현재 로그인한 사용자 정보를 가져옴
        User user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get())).get();

        // 푸드트럭 정보 가져옴
        Foodtrucks foodtrucks = foodtrucksRepository.findById(foodtrucks_seq)
                .orElseThrow(() -> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));

        // 사용자의 초신 리뷰 조회
        Optional<Review> lastReview = reviewRepository.findTopByUserAndFoodtrucksOrderByCreatedDateDesc(user, foodtrucks);

        if(lastReview.isPresent()){
            // 마지막 리뷰를 작성한 날짜와 오늘 날짜를 비교
            LocalDateTime lastReviewDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastReview.get().getCreatedDate()), ZoneId.systemDefault());
            LocalDate today = LocalDate.now();

            // 이미 오늘 리뷰를 작성했다면 예외처리
            if(lastReviewDate.toLocalDate().isEqual(today)){
                throw new Exceptions(ErrorCode.REVIEW_TODAY_EXIST);
            }
        }


        // 생성 시간
        Long createdDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // 리뷰 저장하기
        Review review = Review.builder()
                .rvIsDelicious(reviewReqDto.getRvIsDelicious())
                .rvIsCool(reviewReqDto.getRvIsCool())
                .rvIsClean(reviewReqDto.getRvIsClean())
                .rvIsKind(reviewReqDto.getRvIsKind())
                .rvIsSpecial(reviewReqDto.getRvIsSpecial())
                .rvIsCheap(reviewReqDto.getRvIsCheap())
                .rvIsFast(reviewReqDto.getRvIsFast())
                .createdDate(createdDate)
                .user(user)
                .foodtrucks(foodtrucks)
                .build();
        reviewRepository.save(review);

        return ReviewResDto.fromEntity(review);
    }

    @Override
    public ReviewSummaryDto getReviews(Long foodtrucks_seq) {
        List<Review> reviews = reviewRepository.findByFoodtrucksId(foodtrucks_seq);

        ReviewSummaryDto summary = new ReviewSummaryDto();

        // 총 리뷰 개수 설정
        summary.setTotalReviews((long) reviews.size());

        // 각 항목별 리뷰 개수를 집계
        summary.setRvIsDelicious(reviews.stream().filter(Review::getRvIsDelicious).count());
        summary.setIsCool(reviews.stream().filter(Review::getRvIsCool).count());
        summary.setIsClean(reviews.stream().filter(Review::getRvIsClean).count());
        summary.setIsKind(reviews.stream().filter(Review::getRvIsKind).count());
        summary.setRvIdSpecial(reviews.stream().filter(Review::getRvIsSpecial).count());
        summary.setRvIsCheap(reviews.stream().filter(Review::getRvIsCheap).count());
        summary.setRvIsFast(reviews.stream().filter(Review::getRvIsFast).count());
        return summary;
    }

    @Override
    public List<Map<String,Object>> getReviewlist(Long foodtruckId){
        List<Map<String,Object>> reviews = new ArrayList<>();

        Map<String, Object> review1 = new HashMap<>();
        review1.put("id", "rvIsDelicious");
        review1.put("cnt", reviewRepository.countDeliciousReviewsByFoodtruckId(foodtruckId));

        Map<String, Object> review2 = new HashMap<>();
        review2.put("id", "rvIsSpecial");
        review2.put("cnt", reviewRepository.countSpecialReviewsByFoodtruckId(foodtruckId));

        Map<String, Object> review3 = new HashMap<>();
        review3.put("id", "rvIsCheap");
        review3.put("cnt", reviewRepository.countChipReviewsByFoodtruckId(foodtruckId));

        Map<String, Object> review4 = new HashMap<>();
        review4.put("id", "rvIsFast");
        review4.put("cnt", reviewRepository.countFastReviewsByFoodtruckId(foodtruckId));

        Map<String, Object> review5 = new HashMap<>();
        review5.put("id", "rvIsClean");
        review5.put("cnt", reviewRepository.countCleanReviewsByFoodtruckId(foodtruckId));

        Map<String, Object> review6 = new HashMap<>();
        review6.put("id", "rvIsCool");
        review6.put("cnt", reviewRepository.countCoolReviewsByFoodtruckId(foodtruckId));

        Map<String, Object> review7 = new HashMap<>();
        review7.put("id", "rvIsKind");
        review7.put("cnt", reviewRepository.countKindReviewsByFoodtruckId(foodtruckId));

        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        reviews.add(review5);
        reviews.add(review6);
        reviews.add(review7);

        return reviews;
    };
}
