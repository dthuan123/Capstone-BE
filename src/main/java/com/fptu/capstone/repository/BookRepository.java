package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "select top(10) book_id, status_id, book_name, overall_rating, total_rating, updated_date, description, like_count, enabled,started_date, alias_id, creator_id, image_link from Book", nativeQuery = true)
    List<Book> findAll();
    Book findById(int id);
    Page<Book> findByCategoriesId(int categoryId, Pageable pageable);
}
