package com.fptu.capstone.repository;

import com.fptu.capstone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsUserByName(String name);
    User findByNameAndPassword(String name, String password);
    List<User> findById(int id);
    User findByName(String name);
    User save(User user);
    User getById(int id);
}
