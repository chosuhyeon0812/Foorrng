package com.d205.foorrng.bigdata.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.aot.generate.Generated;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Boundary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String areaName;
    private Double latitude;
    private Double longitude;
}
