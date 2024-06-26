package com.d205.foorrng.user.repository;

import com.d205.foorrng.user.entity.FavoriteFood;
import com.d205.foorrng.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUserUid(Long userUid);


    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    Optional<User> findByBusinessNumber(String businessNumber);
}
