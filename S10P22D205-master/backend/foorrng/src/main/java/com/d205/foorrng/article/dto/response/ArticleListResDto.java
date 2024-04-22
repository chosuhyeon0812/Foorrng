package com.d205.foorrng.article.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListResDto {
    private long id;
    private String title;
    private String content;
    private double latitude;
    private double longitude;
    private String address;
    private String mainImage;

}
