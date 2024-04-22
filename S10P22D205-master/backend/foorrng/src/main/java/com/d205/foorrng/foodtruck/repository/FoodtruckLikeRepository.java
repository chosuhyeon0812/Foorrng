package com.d205.foorrng.foodtruck.repository;

import com.d205.foorrng.foodtruck.entity.FoodtruckLike;
import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface FoodtruckLikeRepository extends JpaRepository<FoodtruckLike, Long> {

    Optional<List<FoodtruckLike>> findAllByUser(User user);
    Optional<FoodtruckLike> findByUserAndFoodtrucks(User user, Foodtrucks foodtrucks);
}
