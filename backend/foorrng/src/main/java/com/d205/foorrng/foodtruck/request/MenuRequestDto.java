package com.d205.foorrng.foodtruck.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuRequestDto {
    @NotNull
    private String name;
    @NotNull
    private Long price;
    @NotNull
    private Long foodtruck;
}
