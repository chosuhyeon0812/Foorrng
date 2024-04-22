package com.d205.foorrng.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

// 선호하는 음식
@Entity
@Getter
@Builder
@Setter
@Validated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FavoriteFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_seq")
    private Long id;

    private String menu;    // 선호하는 음식 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_seq")
    @JsonIgnore
    private User user;

    private Double latitude;

    private Double longitude;

    private String createdTime;

    private String city;
}
