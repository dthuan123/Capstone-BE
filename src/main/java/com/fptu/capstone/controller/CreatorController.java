package com.fptu.capstone.controller;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Chapter;
import com.fptu.capstone.entity.User;
import com.fptu.capstone.repository.BookRepository;
import com.fptu.capstone.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("creator")
public class CreatorController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ChapterRepository chapterRepository;


    @ResponseBody
    @GetMapping("book-list")
    public Page<Book> getBookListByCreator(@RequestHeader int page, @RequestHeader int pageSize,
                                           @RequestHeader String sortField, @RequestHeader String sortOrder) {
        Sort sort = Sort.by(sortField).ascending();
        if(sortOrder == "des") {
            sort.descending();
        }

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return bookRepository.findAll(pageable);
    }

    @ResponseBody
    @PostMapping(value="create-book")
    public boolean createBook(@RequestBody Book book) {
        bookRepository.save(book);
        return true;
    }

    @ResponseBody
    @GetMapping(value="book-edit")
    public List<Chapter> getChapter(@RequestHeader int bookId) {
        return chapterRepository.findChapterByBookId(bookId);
    }
}
