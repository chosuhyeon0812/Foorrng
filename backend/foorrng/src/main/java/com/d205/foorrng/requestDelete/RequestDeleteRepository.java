package com.d205.foorrng.requestDelete;

import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestDeleteRepository extends JpaRepository<RequestDelete, Long> {

    Optional<RequestDelete> findByUserAndFoodtrucks(User user, Foodtrucks foodtrucks);
    List<RequestDelete> findAllByFoodtrucks(Foodtrucks foodtrucks);
}
