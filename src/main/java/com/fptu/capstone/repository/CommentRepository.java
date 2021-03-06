package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllCommentsByBookIdAndParentIdIsNull(int id);
    List<Comment> findAll();
    Page<Comment> findAllCommentsByBookIdAndParentIdIsNull(Pageable pageable, int id);
    List<Comment> findAllByOrderByStartedDateDesc();

}
