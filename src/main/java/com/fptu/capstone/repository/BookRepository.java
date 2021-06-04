package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findAll();

}
