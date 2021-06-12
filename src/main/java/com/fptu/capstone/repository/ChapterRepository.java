package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    List<Chapter> findChapterByBookId(int id);
}
