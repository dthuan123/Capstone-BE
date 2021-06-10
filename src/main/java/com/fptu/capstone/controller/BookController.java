package com.fptu.capstone.controller;

import com.fptu.capstone.entity.Book;
//import com.example.demo_be.entity.Comment;
import com.fptu.capstone.entity.Comment;
import com.fptu.capstone.entity.User;
import com.fptu.capstone.repository.BookRepository;
//import com.example.demo_be.repository.CommentRepository;
import com.fptu.capstone.repository.CommentRepository;
import com.fptu.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;
    @ResponseBody
    @GetMapping("/books")
    public List<Book> getALlBooks() {
        return bookRepository.findById(1);
    }

    @ResponseBody
    @GetMapping("/com")
    public Page<Comment> getAllComments() {
        Pageable pageable = PageRequest.of(0, 2);
        return commentRepository.findAllCommentsByBookIdAndParentIdIsNull(pageable, 1);
        //return commentRepository.findAllCommentsByBookIdAndParentIdIsNull(1);
    }

    @ResponseBody
    @GetMapping("/user")
    public List<Book> getUser() {
        return userRepository.findById(2).get(0).getBooks();
    }

}
