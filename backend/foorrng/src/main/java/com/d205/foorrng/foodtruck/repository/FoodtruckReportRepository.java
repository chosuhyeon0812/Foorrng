package com.d205.foorrng.foodtruck.repository;

import com.d205.foorrng.foodtruck.entity.FoodtruckId;
import com.d205.foorrng.foodtruck.entity.FoodtruckReport;
import com.d205.foorrng.foodtruck.entity.FoodtruckReportId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodtruckReportRepository extends JpaRepository<FoodtruckReport, Long> {
    Optional<FoodtruckReport> findByFoodtruckId(FoodtruckReportId foodtruckReportId);

}
