package com.d205.foorrng.food.service;

import com.d205.foorrng.common.exception.ErrorCode;
import com.d205.foorrng.common.exception.Exceptions;
import com.d205.foorrng.food.Food;
import com.d205.foorrng.food.dto.FavoritefoodDto;
import com.d205.foorrng.food.repository.FavoritefoodRepository;
import com.d205.foorrng.food.repository.FoodRepository;
import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.foodtruck.repository.FoodtrucksRepository;
import com.d205.foorrng.user.dto.RegistDto;
import com.d205.foorrng.user.entity.FavoriteFood;
import com.d205.foorrng.user.entity.User;
import com.d205.foorrng.user.repository.UserRepository;
import com.d205.foorrng.util.SecurityUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.thirdparty.jackson.core.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter @Setter //?
@Service
@RequiredArgsConstructor
public class FoodService {

    private final UserRepository userRepository;
    private final FavoritefoodRepository favoritefoodRepository;
    private final FoodtrucksRepository foodtrucksRepository;
    private final FoodRepository foodRepository;
    @Value("${externalService.geoApiKey}")
    private String apikey;
    @Transactional
    public String getCityNameByPoint(Double latitude, Double longitude) {

        String searchType = "road";
        String searchPoint = longitude.toString() + "," + latitude.toString();
        String epsg = "epsg:4326";

        StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
        sb.append("?service=address");
        sb.append("&request=getaddress");
        sb.append("&format=json");
        sb.append("&crs=" + epsg);
        sb.append("&key=" + apikey);
        sb.append("&type=" + searchType);
        sb.append("&point=" + searchPoint);

        try{
            JSONParser jspa = new JSONParser();
            JSONObject jsob = (JSONObject) jspa.parse(new BufferedReader(new InputStreamReader(new URL(sb.toString()).openStream(), StandardCharsets.UTF_8)));
            JSONObject jsrs = (JSONObject) jsob.get("response");
            JSONArray jsonArray = (JSONArray) jsrs.get("result");
            JSONObject jsonfor = new JSONObject();

            for (int i = 0; i< jsonArray.size(); i++){
                jsonfor = (JSONObject) jsonArray.get(i);
                System.out.println(jsonfor.get("text"));

            }
            return jsonfor.get("text").toString().substring(jsonfor.get("text").toString().indexOf("(") + 1, jsonfor.get("text").toString().indexOf(","));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

    }


    public void saveFavoriteFood(FavoritefoodDto favoritefoodDto) {

        User user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get())).get();
        List<String> FavoriteFoodList = favoritefoodDto.getFavoriteFoods();

        for (String food: FavoriteFoodList) {
            FavoriteFood favoriteFood = FavoriteFood.builder()
                    .user(user)
                    .menu(food)
                    .latitude(favoritefoodDto.getLatitude())
                    .longitude(favoritefoodDto.getLongitude())
                    .createdTime(LocalDate.now(ZoneId.of("Asia/Seoul")).toString())
//                    .city(getCityNameByPoint(favoritefoodDto.getLatitude(),
//                            favoritefoodDto.getLongitude()))
                    .build();
            favoritefoodRepository.save(favoriteFood);
        }
    }

    @Transactional
    public List<String> updateFavoriteFood(FavoritefoodDto favoritefoodDto) {
        User user = userRepository.findByUserUid(Long.parseLong(SecurityUtil.getCurrentUsername().get())).get();

        String todayDate = LocalDate.now(ZoneId.of("Asia/Seoul")).toString();

        favoritefoodRepository.deleteAllByUserAndCreatedTime(user, todayDate);

        List<String> FavoriteFoodList = favoritefoodDto.getFavoriteFoods();

        for (String food: FavoriteFoodList) {
            FavoriteFood favoriteFood = FavoriteFood.builder()
                    .user(user)
                    .menu(food)
                    .latitude(favoritefoodDto.getLatitude())
                    .longitude(favoritefoodDto.getLongitude())
                    .createdTime(LocalDate.now(ZoneId.of("Asia/Seoul")).toString())
                    .build();
            favoritefoodRepository.save(favoriteFood);
        }

        return FavoriteFoodList;
    }

    public void saveFoodtruckFood(Long Id, List<String> FoodtruckFoodList){
        Foodtrucks foodtrucks = foodtrucksRepository.findById(Id).get();

        for(String food: FoodtruckFoodList){
            Food foodtruckfood = Food.builder()
                    .name(food)
                    .foodtrucks(foodtrucks)
                    .build();
            foodRepository.save(foodtruckfood);
        }
    }

    public List<String> getFoodtruckFood(Long foodtruckId){
        Foodtrucks foodtrucks = foodtrucksRepository.findById(foodtruckId)
                .orElseThrow(() -> new Exceptions(ErrorCode.FOODTRUCK_NOT_EXIST));
        List<Food> foods = foodRepository.findAllByFoodtrucks(foodtrucks);
        List<String> category = new ArrayList<>();
        for(Food food:foods){
            category.add(food.getName());
        }
        return category;
    }

}
