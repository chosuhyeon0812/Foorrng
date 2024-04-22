package com.d205.foorrng.bigdata.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Entity
@Getter
@Setter
@Validated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bigdata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bigdata_seq")
    private Long id;
    private String city;
    private String food;
    private Double score;
}
