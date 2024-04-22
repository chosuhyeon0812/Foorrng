package com.d205.foorrng.mark.repository;

import com.d205.foorrng.foodtruck.entity.Foodtrucks;
import com.d205.foorrng.mark.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    Optional<Mark> findById(Long id);
    List<Mark> findAllByLatitudeBetweenAndLongitudeBetween(double latitude1, double latitude2, double longitude1, double longitude2);
    List<Mark> findByFoodtrucks(Foodtrucks foodtrucks);
    Optional<List<Mark>> findAllByFoodtrucksId(Long foodtruckId);
}
