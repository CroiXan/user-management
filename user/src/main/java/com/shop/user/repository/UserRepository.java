package com.shop.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.user.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

    @Query(value = "SELECT * FROM user us WHERE us.email = ?1 AND us.password = ?2", nativeQuery = true)
    List<User> getUserWithPassword(@Param("email") String email, @Param("password") String password);
}
