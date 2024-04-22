package com.d205.foorrng.article.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 유효성
public class ArticleUpdateReqDto {
    @NotNull
    private Long articleId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotBlank
    private String phone;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String kakaoId;
    @NotBlank
    private String organizer;
    @NotNull
    private Long startDate;
    @NotNull
    private Long endDate;
    @NotBlank
    private String address;
}
