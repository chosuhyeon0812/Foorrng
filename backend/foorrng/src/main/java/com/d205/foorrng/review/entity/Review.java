package com.d205.foorrng.review.entity;

import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Entity
@Getter
@Setter
@Validated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_seq")
    private Long id;
    private Boolean rvIsDelicious;    // 음식이 맛있어요
    private Boolean rvIsCool;            // 푸드트럭이 멋져요
    private Boolean rvIsClean;           // 매장이 청결해요
    private Boolean rvIsKind;            // 사장님이 친절해요
    private Boolean rvIsSpecial;      // 특별한 메뉴가 잇어요
    private Boolean rvIsCheap;         // 가성비가 좋아요
    private Boolean rvIsFast;         // 음식이 빨리 나와요
    private Long createdDate;          // 생성 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "foodtruck_seq")
    private Foodtrucks foodtrucks;

    @Builder
    public Review(Boolean rvIsDelicious, Boolean rvIsCool, Boolean rvIsClean,
                  Boolean rvIsKind, Boolean rvIsSpecial, Boolean rvIsCheap,
                  Boolean rvIsFast, Long createdDate, User user,
                  Foodtrucks foodtrucks){
        this.rvIsDelicious = rvIsDelicious;
        this.rvIsCool = rvIsCool;
        this.rvIsClean = rvIsClean;
        this.rvIsKind = rvIsKind;
        this.rvIsSpecial = rvIsSpecial;
        this.rvIsCheap = rvIsCheap;
        this.rvIsFast = rvIsFast;
        this.createdDate = createdDate;
        this.user = user;
        this.foodtrucks = foodtrucks;
    }

}
