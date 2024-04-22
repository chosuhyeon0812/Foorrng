package com.d205.foorrng.food.repository;


import com.d205.foorrng.user.entity.FavoriteFood;
import com.d205.foorrng.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritefoodRepository extends JpaRepository<FavoriteFood, Long> {

    Optional<List<FavoriteFood>> findAllByUser(User user);

    Optional<List<FavoriteFood>> findAllByUserAndCreatedTime(User user, String createdTime);

    Integer deleteAllByUserAndCreatedTime(User user, String createdTime);
}
