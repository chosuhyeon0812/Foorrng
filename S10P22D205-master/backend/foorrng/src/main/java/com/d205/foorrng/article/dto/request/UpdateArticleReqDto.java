package com.d205.foorrng.article.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleReqDto {


    private String title;

    private String content;

    private Long create_datetime;

    private Long updated_datetime;

    private double latitude;

    private double longitude;





}
