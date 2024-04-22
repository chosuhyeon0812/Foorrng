package com.d205.foorrng.foodtruck.request;

import com.d205.foorrng.foodtruck.entity.FoodtruckId;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class FoodtruckUpdateReqDto {
    private Long foodtruckId;
    private String announcement;
    @NotBlank
    private String name;
    private String accountInfo;
    @NotBlank
    private String carNumber;
    private String phoneNumber;
    private List<String> category;
}