package com.fptu.capstone.repository;

import com.fptu.capstone.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT r FROM User r  WHERE  r.name like %?1%")
    Page<User> findAllByName(String name, Pageable pageable);
    boolean existsUserByName(String name);
    User findByNameAndPassword(String name, String password);
    List<User> findById(int id);
    User findByName(String name);
    User save(User user);
    List<User> findUserByRoleIdAndNameContains(int role, String searchword);

    @Query("UPDATE User SET password = :password where id = :id")
    User updatePassword(@Param("password") String password, @Param("id") int userId);
}
