package com.d205.foorrng.food;

import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Entity
@Getter
@Setter
@Validated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_seq")
    private Long id;

    private String name;      // 음식 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="foodtrucks_seq")
    private Foodtrucks foodtrucks;
}
