package com.d205.foorrng.foodtruck.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.d205.foorrng.common.exception.ErrorCode;
import com.d205.foorrng.common.exception.Exceptions;
import com.d205.foorrng.food.Food;
import com.d205.foorrng.food.repository.FoodRepository;
import com.d205.foorrng.food.service.FoodService;
import com.d205.foorrng.foodtruck.entity.Foodtruck;
import com.d205.foorrng.foodtruck.entity.FoodtruckId;
import com.d205.foorrng.foodtruck.entity.FoodtruckRole;
import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.foodtruck.repository.FoodtruckRepository;
import com.d205.foorrng.foodtruck.repository.FoodtrucksRepository;
import com.d205.foorrng.foodtruck.request.FoodtruckCreateReqDto;
import com.d205.foorrng.foodtruck.request.FoodtruckUpdateReqDto;
import com.d205.foorrng.foodtruck.response.FoodtruckResDto;
import com.d205.foorrng.user.entity.User;
import com.d205.foorrng.user.repository.UserRepository;
import com.d205.foorrng.util.S3Image;
import com.d205.foorrng.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodtruckServiceImpl implements FoodtruckService{
    private final FoodtruckRepository foodtruckRepository;
    private final FoodtrucksRepository foodtrucksRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region}")
    private String region;
    private final AmazonS3Client amazonS3Client;
    private final S3Image s3Image;
    private final FoodService foodService;

    @Override
    @Transactional
    public FoodtruckResDto createFoodtruck( FoodtruckCreateReqDto foodtruckCreateReqDto, MultipartFile picture) throws IOException {
        User user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get())).get();
        // 전체 푸드트럭 entity 생성
        Foodtrucks foodtrucks = Foodtrucks.builder()
                .user(user)
                .foodtruckRole(FoodtruckRole.Foodtruck).build();
        foodtrucksRepository.save(foodtrucks);
        // 생성날짜 long 타입
        Long createdDay = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        // 점주 푸드트럭 entity 생성
        Foodtruck foodtruck = Foodtruck.builder()
                .foodtruckId(new FoodtruckId(foodtrucks.getId()))
                .name(foodtruckCreateReqDto.getName())
                .announcement(foodtruckCreateReqDto.getAnnouncement())
                .accountInfo(foodtruckCreateReqDto.getAccountInfo())
                .carNumber(foodtruckCreateReqDto.getCarNumber())
                .phoneNumber(foodtruckCreateReqDto.getPhoneNumber())
                .createdDay(createdDay)
                .foodtrucks(foodtrucks)
                .build();
        // 이미지 s3 저장
        String imgUrl = "";
        if(picture != null) {
            String imgName = foodtruck.getName() + foodtrucks.getId().toString() + ".png";
            String dir = "/foodtruckIMG";
            imgUrl = s3Image.saveImageS3(picture, imgName, dir);
        }
        foodtruck.updatePicture(imgUrl);
        // 푸드트럭 음식카테고리
        foodService.saveFoodtruckFood(foodtrucks.getId(), foodtruckCreateReqDto.getCategory());
        // 푸드트럭 저장
        foodtruckRepository.save(foodtruck);
        return new FoodtruckResDto(foodtruck, foodtrucks.getId(), foodtruckCreateReqDto.getCategory(), user.getBusinessNumber());
    };

    @Override
    @Transactional
    public FoodtruckResDto updateFoodtruck(FoodtruckUpdateReqDto foodtruckUpdateReqDto, MultipartFile picture) throws IOException{
        User user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get())).get();
        Foodtrucks foodtrucks = foodtrucksRepository.findById(foodtruckUpdateReqDto.getFoodtruckId())
                .orElseThrow(() -> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));
        Foodtruck foodtruck = foodtruckRepository.findByFoodtruckId(new FoodtruckId(foodtrucks.getId()))
                .orElseThrow(() -> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));

        foodtruck.updateAnnouncement(foodtruckUpdateReqDto.getAnnouncement());
        foodtruck.updateName(foodtruckUpdateReqDto.getName());
        foodtruck.updateAccountInfo(foodtruckUpdateReqDto.getAccountInfo());
        foodtruck.updateCarNumber(foodtruckUpdateReqDto.getCarNumber());
        foodtruck.updatePhoneNumber(foodtruckUpdateReqDto.getPhoneNumber());


        if(picture!=null){
            String imgUrl = foodtruck.getPicture();
            String imgName = foodtruck.getName() + foodtrucks.getId().toString() + ".png";
            String dir = "/foodtruckIMG";
            imgUrl = s3Image.saveImageS3(picture, imgName, dir);
            foodtruck.updatePicture(imgUrl);
        }


        // 수정 API 들어오면 음식카테고리는 삭제 및 생성
        // 더 나은 방법이 있을까
        List<Food> foodlist = foodRepository.findAllByFoodtrucks(foodtrucks);
        foodRepository.deleteAll(foodlist);
        foodService.saveFoodtruckFood(foodtrucks.getId(), foodtruckUpdateReqDto.getCategory());
        foodtruckRepository.save(foodtruck);

        return new FoodtruckResDto(foodtruck, foodtruckUpdateReqDto.getFoodtruckId(), foodtruckUpdateReqDto.getCategory(), user.getBusinessNumber());
    };

    @Override
    @Transactional
    public void deleteFoodtruck(Long foodtruckId){
        Foodtrucks foodtrucks = foodtrucksRepository.findById(foodtruckId)
                .orElseThrow(() -> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));
        foodtrucksRepository.delete(foodtrucks);
    }
}
