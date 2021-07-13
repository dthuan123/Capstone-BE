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
import com.fptu.capstone.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private ScheduleService scheduleService;

    @ResponseBody
    @GetMapping("/top-10-books")
    public List<Book> getTop10Book() {
        return bookRepository.findTop10Book();
    }

    @ResponseBody
    @GetMapping("/newest-books")
    public List<Book> getTop10NewestBook(){
        return bookRepository.findTop10NewestBook();
    }

    @ResponseBody
    @GetMapping("/search-book")
    public List<Book> getBookByContainName(@RequestHeader String searchword){
        return bookRepository.findBookByNameContains(searchword);
    }

    @ResponseBody
    @GetMapping("/all-books")
    public Page<Book> getAllBook(@RequestHeader int page, @RequestHeader int pageSize, @RequestHeader String sort){
        Sort sortField = Sort.by("id");
        if(sort.equals("newupdate")) {
            sortField = Sort.by("updatedDate").descending();
        } else if(sort.equals("date")){
            sortField = Sort.by("startedDate").descending();
        } else if(sort.equals("likes")){
            sortField = Sort.by("likes").descending();
        } else {
            sortField = Sort.by("name").ascending();
        }
        Pageable pageable = PageRequest.of(page, pageSize, sortField);
        return bookRepository.findAll(pageable);
    }

    @ResponseBody
    @GetMapping("/book-by-id")
    public Book getBookById(@RequestHeader int bookId){
        return bookRepository.findById(bookId);
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
        scheduleService.publishChapters(bookId);
        return chapterRepository.findChapterByBookId(bookId, pageable);
    }

    @ResponseBody
    @GetMapping("/get-top-newest-comment-book")
    public List<Comment> getTopNewestComment(){
        List<Comment> comments = commentRepository.findAll();
        List<Comment> commentList = new ArrayList<>();
        for(int i=0; i<10; i++){
            commentList.add(comments.get(i));
        }
        return commentList;
    }

    @ResponseBody
    @GetMapping("/list-book-by-creator")
    public Page<Book> getAllBookByCreatorId(@RequestHeader int creatorId, @RequestHeader int page, @RequestHeader int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return bookRepository.findALlByCreatorId(creatorId, pageable);
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
                //System.out.println("Ban da like roi");
                book.setLikes(likeCount - 1);
                likedList.remove(book);
                userRepository.save(user);
                bookRepository.save(book);
            }else {
                book.setLikes(likeCount + 1);
                //update user;
                likedList.add(book);
                userRepository.save(user);
                bookRepository.save(book);
            }
        return book;
    }

    @ResponseBody
    @GetMapping("/updateRating")
    public Book updateRating(@RequestHeader int newRating, @RequestHeader int bookId) {
        Book book = bookRepository.findById(bookId);
        float overallRating = book.getOverallRating();
        int totalRating = book.getTotalRating();
        book.setOverallRating(((overallRating * totalRating) + newRating) / (totalRating + 1));
        book.setTotalRating(totalRating + 1);
        return bookRepository.save(book);
    }

}
