package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "select top(18) id, status_id, book_name, overall_rating, total_rating, updated_date, description, like_count, enabled,started_date, alias_id, creator_id, image_link from Book where enabled = 1 order by like_count desc", nativeQuery = true)
    List<Book> findTop10Book();

    @Query(value = "select top(18) id, status_id, book_name, overall_rating, total_rating, updated_date, description, like_count, enabled,started_date, alias_id, creator_id, image_link from Book where enabled = 1 order by started_date desc", nativeQuery = true)
    List<Book> findTop10NewestBook();
    Book findById(int id);
    Page<Book> findByCategoriesId(int categoryId, Pageable pageable);
    Page<Book> findALlByCreatorId(int id, Pageable pageable);
    Page<Book> findALlByCreatorIdAndNameContains(int id, String name, Pageable pageable);
    Page<Book> findAll(Pageable pageable);

//    @Query(value = "select r from Book r where r.name like '%:searchword%'")
    List<Book> findBookByNameContains(String searchword);

    //    @Query(value = "select r from Book r where r.name like '%:searchword%'")
    List<Book> findBookByEnabledAndNameContains(boolean enabled, String searchword);

    @Query("SELECT r FROM Book r  WHERE  r.name like %?1%")
    Page<Book> findAllByName(String name, Pageable pageable);

    @Query(value = "select * " +
            " from Book c inner join UserLikeList b on c.id = b.book_id " +
            "where b.user_id = ?1 and enabled = 1", nativeQuery = true)

//    @Query(value = "select from Book ", nativeQuery = true)
    Page<Book> findLikeList (int userId, Pageable pageable);
}
