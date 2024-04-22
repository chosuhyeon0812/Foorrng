package com.d205.foorrng.article.entity;

import com.d205.foorrng.article.CryptoConverter;
import com.d205.foorrng.article.dto.request.ArticleReqDto;
import com.d205.foorrng.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Entity
@Getter
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    private String title;

    private String content;

    @CreatedDate
    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    @LastModifiedDate
    @Column(name = "updated_datetime")
    private LocalDateTime updatedDatetime;

    private double latitude;

    private double longitude;

    @Convert(converter = CryptoConverter.class)
    private String phone;               // 작성자 전화번호

    @Convert(converter = CryptoConverter.class)
    private String email;               // 작성자 이메일

    @Convert(converter = CryptoConverter.class)
    private String kakaoID;             // 작성자 카카오 ID

    private String organizer;           // 주최측

    @NotNull
    private Long startDate;

    @NotNull
    private Long endDate;

    @NotNull
    private String address;


    private String mainImage;             // 행사 이미지

}



//    public Article(ArticleReqDto articleReqDto, String image ){
//        //유저에 대한 정보를 저장을 어따하지 ?
//        this.title = articleReqDto.getTitle();
//        this.content = articleReqDto.getContent();
//        this.latitude = articleReqDto.getLatitude();
//        this.longitude = articleReqDto.getLongitude();
//        this.phone = articleReqDto.getPhone();
//        this.email = articleReqDto.getEmail();
//        this.kakaoID = articleReqDto.getKakaoId();
//        this.organizer = articleReqDto.getOrganizer();
//        this.startDate = articleReqDto.getStart_date();
//        this.endDate = articleReqDto.getEnd_date();
//        this.address = articleReqDto.getAddress();
//        this.picture = image;
//    }
//    public Article(User user, String title, String content, Double latitude,
//                   Double longitude, String phone, String email, String kakaoID,
//                   String organizer, Long startDate, Long endDate, String address, String picture) {
//        this.user = user;
//        this.title = title;
//        this.content = content;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.phone = phone;
//        this.email = email;
//        this.kakaoID = kakaoID;
//        this.organizer = organizer;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.address = address;
//        this.picture = picture;
//    }
//    public static Article of(long userId, String title, String content, Double latitude,
//                   Double longitude, String phone, String email, String kakaoID,
//                   String organizer, Long startDate, Long endDate, String address, String picture) {
//        return Article.builder()
//                .id(userId)
//                .title(title)
//                .email(email)
//                .address(address)
//                .content(content)
//                .phone(phone)
//                .latitude(latitude)
//                .longitude(longitude)
//                .picture(picture)
//                .build();
//    }


//    private Long currentDateTime(){
//        return System.currentTimeMillis();
//    }
