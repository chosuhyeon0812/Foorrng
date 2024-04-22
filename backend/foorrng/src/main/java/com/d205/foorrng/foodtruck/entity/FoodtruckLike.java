package com.d205.foorrng.foodtruck.entity;

import com.d205.foorrng.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Entity
@Getter
@Setter
@Validated
@NoArgsConstructor
public class FoodtruckLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "foodtruck_like_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "foodtrucks_seq")
    private Foodtrucks foodtrucks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;


    @Builder
    public  FoodtruckLike(User user, Foodtrucks foodtrucks){
        this.user = user;
        this.foodtrucks = foodtrucks;
    }
}
