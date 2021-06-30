package com.fptu.capstone.controller;

import com.fptu.capstone.entity.Book;
//import com.example.demo_be.entity.Comment;
import com.fptu.capstone.entity.Chapter;
import com.fptu.capstone.entity.Comment;
import com.fptu.capstone.entity.User;
import com.fptu.capstone.repository.BookRepository;
//import com.example.demo_be.repository.CommentRepository;
import com.fptu.capstone.repository.ChapterRepository;
import com.fptu.capstone.repository.CommentRepository;
import com.fptu.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private ChapterRepository chapterRepository;

    @ResponseBody
    @GetMapping("/books")
    public List<Book> getALlBooks() {
        return bookRepository.findAll();
    }

    @ResponseBody
    @GetMapping("/book-by-id")
    public Book getBookById(@RequestHeader int bookId){
        return bookRepository.findById(bookId);
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
    public User getUser() {
        return userRepository.findById(1).get(0);
    }

    @ResponseBody
    @GetMapping("/chapter")
    public Page<Chapter> getAllChapterOfBook(@RequestHeader int bookId, @RequestHeader int page, @RequestHeader int pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return chapterRepository.findChapterByBookId(bookId, pageable);
    }

    @ResponseBody
    @GetMapping("/updateLike")
    public Book updateLike(@RequestHeader int likeCount, @RequestHeader int bookId, @RequestHeader int userId ) {
        User user = userRepository.findById(userId).get(0);
        List<Book> likedList = user.getLikedList();
        Book book = bookRepository.findById(bookId);
        boolean isLiked = false;

        for (Book bookLiked : likedList) {
            if(bookLiked.getId() == book.getId()){
                isLiked = true;
                break;
            }
        }
            if(isLiked){
                System.out.println("Ban da like roi");
            }else {
                book.setLikes(likeCount + 1);
                //update user;
                likedList.add(book);
                userRepository.save(user);
                bookRepository.save(book);
            }
        return book;
    }

}
