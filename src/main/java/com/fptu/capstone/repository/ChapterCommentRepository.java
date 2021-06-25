package com.fptu.capstone.repository;

import com.fptu.capstone.entity.ChapterComment;
import com.fptu.capstone.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterCommentRepository extends JpaRepository<ChapterComment, Integer> {
    Page<ChapterComment> findAllCommentsByChapterIdAndParentIdIsNull(Pageable pageable, int id);
}
