package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAll();
    List<Book> findById(int id);
    //List<Book> findByCategory(int categoryId);
}
