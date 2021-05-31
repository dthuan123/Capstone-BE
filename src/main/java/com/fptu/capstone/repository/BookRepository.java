package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Book;
//import com.example.demo_be.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findAll();

}
