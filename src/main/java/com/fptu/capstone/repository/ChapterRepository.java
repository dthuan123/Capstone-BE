package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Chapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    Page<Chapter> findChapterByBookId(int id, Pageable pageable);
    Page<Chapter> findALlByBookIdAndNameContains(int id, String name, Pageable pageable);
    @Query(value = "select MAX(chapter_id) from Chapter", nativeQuery = true)
    int findMaxChapterId();
    Chapter findById(int id);
    List<Chapter> findByBookIdAndChapterStatusId(int bookId, int statusId);

    @Query(value = "select * from Chapter c inner join UserBookHistory b on c.chapter_id = b.chapter_id where b.user_id = ?1", nativeQuery = true)
    Page<Chapter> findHistoryList(int userId, Pageable pageable);

    Page<Chapter> findChapterByBookIdAndChapterStatusId(int id, int statusId, Pageable pageable);
}
