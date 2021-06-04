package com.fptu.capstone.repository;

import com.fptu.capstone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsUserByName(String name);
    List<User> findByName(String name);
}
