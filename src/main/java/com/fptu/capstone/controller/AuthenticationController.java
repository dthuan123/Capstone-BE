package com.fptu.capstone.controller;

import com.fptu.capstone.entity.ChapterComment;
import com.fptu.capstone.entity.Comment;
import com.fptu.capstone.entity.User;
import com.fptu.capstone.repository.ChapterCommentRepository;
import com.fptu.capstone.repository.CommentRepository;
import com.fptu.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChapterCommentRepository chapterCommentRepository;

    @ResponseBody
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User userDB = userRepository.findByNameAndPassword(user.getName(), user.getPassword());
        if(userDB != null) {
            userDB.setPassword(null);
            return userDB;
        }

        return null;
    }
    @ResponseBody
    @PostMapping(value="/register")
    public ResponseEntity registerUser(@RequestBody User user){
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @ResponseBody
    @GetMapping(value="/comments")
    public Page<ChapterComment> getCommentsByChapter(@RequestHeader int chapterId){
        Pageable pageable = PageRequest.of(0, 5);
        return chapterCommentRepository.findAllCommentsByChapterIdAndParentIdIsNull(pageable, 2);
    }
}
