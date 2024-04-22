package com.d205.foorrng.bigdata.service;

import com.d205.foorrng.bigdata.dto.BoundaryDto;
import com.d205.foorrng.bigdata.dto.RecommendDto;
import com.d205.foorrng.bigdata.dto.VillageInfoDto;
import com.d205.foorrng.bigdata.entity.Bigdata;
import com.d205.foorrng.bigdata.entity.Boundary;
import com.d205.foorrng.bigdata.repository.BigdataRepository;
import com.d205.foorrng.bigdata.repository.BoundaryRepository;
import com.d205.foorrng.common.exception.ErrorCode;
import com.d205.foorrng.common.exception.Exceptions;
import com.d205.foorrng.food.Food;
import com.d205.foorrng.food.repository.FoodRepository;
import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.foodtruck.repository.FoodtrucksRepository;
import com.d205.foorrng.user.entity.User;
import com.d205.foorrng.user.repository.UserRepository;
import com.d205.foorrng.util.SecurityUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Getter @Setter
@RequiredArgsConstructor
public class BigdataService {

    private final UserRepository userRepository;
    private final FoodtrucksRepository foodtrucksRepository;
    private final FoodRepository foodRepository;
    private final BoundaryRepository boundaryRepository;
    private final BigdataRepository bigdataRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<RecommendDto> recommendPosition(){
        User user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get())).get();
        Foodtrucks foodtrucks = foodtrucksRepository.findByUserUserUid(user.getUserUid())
                .orElseThrow(()->new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));
        List<Food> category = foodRepository.findAllByFoodtrucks(foodtrucks);

        List<RecommendDto> positionlist = new ArrayList<>();
        for(Food food:category){
            List<Bigdata> bigdataList = bigdataRepository.findByFoodOrderByScoreDesc(food.getName());
            List<VillageInfoDto> villageInfoDtoList = new ArrayList<>();
            for(Bigdata bigdata: bigdataList){
                VillageInfoDto villageInfoDto = new VillageInfoDto(bigdata.getCity(), searchRegionBoundaryPoints(bigdata.getCity()));
                villageInfoDtoList.add(villageInfoDto);
            }
            RecommendDto recommendDto = new RecommendDto(food.getName(), villageInfoDtoList);
            positionlist.add(recommendDto);
        }
        return positionlist;
    }


    public List<BoundaryDto> searchRegionBoundaryPoints(String areaName) {
        List<Boundary> boundaryPoints = boundaryRepository.findAllByAreaName(areaName);
//                .orElseThrow(() -> new Exceptions(ErrorCode.BOUNDARY_NOT_EXIST));

        return boundaryPoints.stream()
                .map(boundary -> new BoundaryDto(boundary.getLatitude(), boundary.getLongitude()))
                .collect(Collectors.toList());
    }





    public void saveDataToCsv() {

        String query = "SELECT menu, latitude, longitude FROM favorite_food";

        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(query);

        String csvFileName = "userSurvey.csv"; // 저장할 CSV 파일 이름

        try (FileWriter writer = new FileWriter(csvFileName)) {
            for (Map<String, Object> data : dataList) {
                // CSV 파일에 데이터 씁니다. 각 필드는 쉼표로 구분됩니다.
                for (Object value : data.values()) {
                    writer.append(value.toString()).append(",");
                }
                writer.append("\n");
            }
            writer.flush();
            System.out.println("Data saved to CSV file: " + csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
