package com.d205.foorrng.foodtruck.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.d205.foorrng.common.exception.ErrorCode;
import com.d205.foorrng.common.exception.Exceptions;
import com.d205.foorrng.food.Food;
import com.d205.foorrng.food.repository.FoodRepository;
import com.d205.foorrng.food.service.FoodService;
import com.d205.foorrng.foodtruck.entity.*;
import com.d205.foorrng.foodtruck.repository.FoodtruckReportRepository;
import com.d205.foorrng.foodtruck.repository.FoodtrucksRepository;
import com.d205.foorrng.foodtruck.request.FoodtruckCreateReqDto;
import com.d205.foorrng.foodtruck.request.FoodtruckUpdateReqDto;
import com.d205.foorrng.foodtruck.response.FoodtruckRepResDto;
import com.d205.foorrng.requestDelete.RequestDelete;
import com.d205.foorrng.requestDelete.RequestDeleteRepository;
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
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodtruckReportServiceImpl implements FoodtruckReportService{

    private final FoodtrucksRepository foodtrucksRepository;
    private final FoodtruckReportRepository foodtruckReportRepository;
    private final UserRepository userRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;
    private final S3Image s3Image;
    private final FoodService foodService;
    private final FoodRepository foodRepository;
    private final RequestDeleteRepository requestDeleteRepository;

    @Override
    @Transactional
    public FoodtruckRepResDto createFoodtruck(FoodtruckCreateReqDto foodtruckCreateReqDto, MultipartFile picture) throws IOException {
        User user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get())).get();

        Foodtrucks foodtrucks = Foodtrucks.builder()
                .user(user)
                .foodtruckRole(FoodtruckRole.FoodtruckReport)
                .build();
        foodtrucksRepository.save(foodtrucks);

        Long createdDay = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        FoodtruckReport foodtruckReport = FoodtruckReport.builder()
                .reportId(new FoodtruckReportId(foodtrucks.getId()))
                .name(foodtruckCreateReqDto.getName())
                .announcement(foodtruckCreateReqDto.getAnnouncement())
                .accountInfo(foodtruckCreateReqDto.getAccountInfo())
                .carNumber(foodtruckCreateReqDto.getCarNumber())
                .phoneNumber(foodtruckCreateReqDto.getPhoneNumber())
                .createdDay(createdDay)
                .foodtrucks(foodtrucks)
                .build();

        String imgUrl = foodtruckReport.getPicture();
        if(picture!=null){
            String imgName = foodtruckCreateReqDto.getName() + foodtrucks.getId() + ".png";
            String dir = "/foodtruckIMG";
            imgUrl = s3Image.saveImageS3(picture, imgName, dir);
        }
        foodtruckReport.updatePicture(imgUrl);

        foodService.saveFoodtruckFood(foodtrucks.getId(), foodtruckCreateReqDto.getCategory());

        foodtruckReportRepository.save(foodtruckReport);
        return new FoodtruckRepResDto(foodtruckReport, foodtrucks.getId(), foodtruckCreateReqDto.getCategory(), "");
    };
    @Override
    @Transactional
    public FoodtruckRepResDto updateFoodtruck(FoodtruckUpdateReqDto foodtruckUpdateReqDto, MultipartFile picture) throws IOException{
        Foodtrucks foodtrucks = foodtrucksRepository.findById(foodtruckUpdateReqDto.getFoodtruckId())
                .orElseThrow(); // 왜 에러코드가 안먹지
        FoodtruckReport foodtruckReport = foodtruckReportRepository.findByFoodtruckId(new FoodtruckReportId(foodtrucks.getId()))
                .orElseThrow(() -> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));

        // 변수 값 수정
        foodtruckReport.updateAnnouncement(foodtruckUpdateReqDto.getAnnouncement());
        foodtruckReport.updateName(foodtruckUpdateReqDto.getName());
        foodtruckReport.updateAccountInfo(foodtruckUpdateReqDto.getAccountInfo());
        foodtruckReport.updatePhoneNumber(foodtruckUpdateReqDto.getPhoneNumber());
        foodtruckReport.updatePhoneNumber(foodtruckUpdateReqDto.getPhoneNumber());
        foodtruckReport.updateCarNumber(foodtruckUpdateReqDto.getCarNumber());

        // 이미지 수정
        if(picture!=null){
            String imgUrl = foodtruckReport.getPicture();
            System.out.println(foodtruckReport.getName());
            String imgName = foodtruckReport.getName() + foodtrucks.getId() + ".png";
            String dir = "/foodtruckIMG";
            imgUrl = s3Image.saveImageS3(picture, imgName, dir);
            foodtruckReport.updatePicture(imgUrl);
        }

        // 카테고리 수정
        List<Food> foodlist = foodRepository.findAllByFoodtrucks(foodtrucks);
        foodRepository.deleteAll(foodlist);
        foodService.saveFoodtruckFood(foodtrucks.getId(), foodtruckUpdateReqDto.getCategory());

        // 수정된 푸드트럭 저장
        foodtruckReportRepository.save(foodtruckReport);

        return new FoodtruckRepResDto(foodtruckReport, foodtrucks.getId(), foodtruckUpdateReqDto.getCategory(), "");
    };
    @Override
    @Transactional
    public int deleteFoodtruck(Long foodtruckId) throws IOException{
        User user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get())).get();
        Foodtrucks foodtrucks = foodtrucksRepository.findById(foodtruckId)
                .orElseThrow(()-> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));

        Optional<RequestDelete> requestDelete = requestDeleteRepository.findByUserAndFoodtrucks(user, foodtrucks);
        if(requestDelete.isPresent()){
            throw new Exceptions(ErrorCode.Foodtruck_Delete_ALREADY_EXIST);
        }

        List<RequestDelete> requestDeletesList = requestDeleteRepository.findAllByFoodtrucks(foodtrucks);
        if(requestDeletesList.size() >=2){
            FoodtruckReport foodtruckReport = foodtruckReportRepository.findByFoodtruckId(new FoodtruckReportId(foodtrucks.getId())).get();
            foodtrucksRepository.delete(foodtrucks);
            foodtruckReportRepository.delete(foodtruckReport); // 왜 cascade가 안되는 걸까
            return 0;
        }else{
            RequestDelete newRequestDelete = RequestDelete.builder()
                    .user(user)
                    .foodtrucks(foodtrucks)
                    .build();
            requestDeleteRepository.save(newRequestDelete);
            return 1;
        }
    };
}
