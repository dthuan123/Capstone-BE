package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAll();
    Page<Category> findAll(Pageable pageable);
    Page<Category> findAllByNameContains(String name, Pageable pageable);
}
