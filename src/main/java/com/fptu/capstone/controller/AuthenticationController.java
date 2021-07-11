package com.fptu.capstone.controller;

import com.fptu.capstone.entity.Book;
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
        List<User> users = userRepository.findAll();
        User duplicate = userRepository.findByName(user.getName());
        if(duplicate == null) {
            user.setAvatarLink("http://localhost:8000/content/images/avatar_images/avatar.jpg");
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return null;
    }

//    @ResponseBody
//    @GetMapping(value="/comments")
//    public Page<ChapterComment> getCommentsByChapter(@RequestHeader int chapterId, @RequestHeader int page,
//                                                     @RequestHeader int pageSize){
//        Pageable pageable = PageRequest.of(page, pageSize);
//        return chapterCommentRepository.findAllCommentsByChapterIdAndParentIdIsNull(pageable, 2);
//    }

    @ResponseBody
    @PostMapping(value = "/changePassword")
    public void changePassword(@RequestPart String password, @RequestPart int userId){
        User user = userRepository.getById(userId);
        user.setPassword(password.replace("\"", ""));
        userRepository.save(user);
    }
}
