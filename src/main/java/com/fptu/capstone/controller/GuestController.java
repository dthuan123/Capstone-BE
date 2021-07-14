package com.fptu.capstone.controller;

import com.fptu.capstone.entity.ChapterComment;
import com.fptu.capstone.entity.Comment;
import com.fptu.capstone.entity.User;
import com.fptu.capstone.repository.ChapterCommentRepository;
import com.fptu.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class GuestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChapterCommentRepository commentRepository;

    @ResponseBody
    @GetMapping("/comments")
    public Page<ChapterComment> getAllComments(@RequestHeader int page, @RequestHeader int pageSize, @RequestHeader int chapterId) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return commentRepository.findAllCommentsByChapterIdAndParentIdIsNull(pageable, chapterId);
    }

}
