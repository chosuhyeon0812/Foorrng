package com.d205.foorrng.article.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
public class ArticleResDto {
    @NotNull
    private Long articleId;
    @NotNull
    private Long userId;
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

    private String mainImage;

    public static ArticleResDto of(long articleId, long userId, String title,
                                   String content, double latitude, double longitude,
                                   String phone, String email, String kakaoId,
                                   String organizer, long startDate, long endDate, String address,String mainImage){
        return builder().articleId(articleId).userId(userId).title(title).content(content)
                .latitude(latitude).longitude(longitude)
                .phone(phone).email(email).kakaoId(kakaoId)
                .organizer(organizer).startDate(startDate).endDate(endDate).address(address).mainImage(mainImage)
                .build();
    }

    @Builder
    public ArticleResDto(long articleId, long userId, String title, String content, double latitude, double longitude,
                         String phone, String email, String kakaoId,
                         String organizer, long startDate, long endDate, String address,String mainImage) {
        this.articleId = articleId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.email = email;
        this.kakaoId = kakaoId;
        this.organizer = organizer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
        this.mainImage = mainImage;
    }

}
