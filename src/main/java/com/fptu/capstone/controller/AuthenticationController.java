package com.fptu.capstone.controller;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.ChapterComment;
import com.fptu.capstone.entity.Comment;
import com.fptu.capstone.entity.User;
import com.fptu.capstone.repository.ChapterCommentRepository;
import com.fptu.capstone.repository.CommentRepository;
import com.fptu.capstone.repository.UserRepository;
import com.fptu.capstone.service.MD5Library;
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

    @Autowired
    private MD5Library md5Library;

    @ResponseBody
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        String md5Password = md5Library.md5(user.getPassword());
        User userDB = userRepository.findByNameAndPassword(user.getName(), md5Password);
        if(userDB != null) {
            userDB.setPassword(null);
            return userDB;
        }

        return userDB;
    }
    @ResponseBody
    @PostMapping(value="/register")
    public ResponseEntity registerUser(@RequestBody User user){
        //List<User> users = userRepository.findAll();
        User duplicate = userRepository.findByName(user.getName());
        String md5Password = md5Library.md5(user.getPassword());
        if(duplicate == null) {
            user.setAvatarLink("http://localhost:8000/content/images/avatar_images/avatar.jpg");
            user.setCoverLink("http://localhost:8000/content/images/cover_images/anhbia.jpg");
            user.setPassword(md5Password);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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
    public ResponseEntity changePassword(@RequestPart String password, @RequestPart int userId, @RequestPart String oldPassword){
        String md5OldPassword = md5Library.md5(oldPassword.replace("\"",""));
        User user = userRepository.getById(userId);
        User userSave = new User();
        if(md5OldPassword.equals(user.getPassword())){
            user.setPassword(md5Library.md5(password.replace("\"", "")));
            userSave = userRepository.save(user);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is not correct.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userSave);
    }
}
