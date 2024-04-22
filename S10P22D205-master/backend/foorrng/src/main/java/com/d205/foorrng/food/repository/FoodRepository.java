package com.d205.foorrng.food.repository;

import com.d205.foorrng.food.Food;
import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByFoodtrucks(Foodtrucks foodtrucks);
}
