package com.fptu.capstone.controller;


import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Report;
import com.fptu.capstone.entity.User;
import com.fptu.capstone.repository.BookRepository;
import com.fptu.capstone.repository.ReportRepository;
import com.fptu.capstone.repository.UserRepository;
import com.fptu.capstone.service.BookEditService;
import com.fptu.capstone.service.UserEditService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("admin")
public class AdminController {
    final UserRepository userRepository;
    final ReportRepository reportRepository;
    final BookRepository bookRepository;
    private final UserEditService userEditService;
    private final BookEditService bookEditService;

    public AdminController(UserRepository userRepository, ReportRepository reportRepository, BookRepository bookRepository, UserEditService userEditService, BookEditService bookEditService) {
        this.userEditService = userEditService;
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
        this.bookRepository = bookRepository;
        this.bookEditService = bookEditService;
    }

    @ResponseBody
    @GetMapping("user-list")
    public Page<User> getAll(@RequestHeader int page, @RequestHeader int pageSize,
                             @RequestHeader String sortField, @RequestHeader String sortOrder) {
        Sort sort = Sort.by(sortField).ascending();
        if(sortOrder == "des") {
            sort.descending();
        }

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return userRepository.findAll(pageable);
    }

    @ResponseBody
    @GetMapping(value="user-edit")
    public List<User> getChapter(@RequestHeader int userid) {
        return userRepository.findById(userid);
    }

    @ResponseBody
    @GetMapping(value="user-approved")
    public boolean approved(@RequestHeader int userid) {
        return userEditService.approved(userid);
    }

    @ResponseBody
    @GetMapping(value="user-enabled")
    public boolean enabled(@RequestHeader int userid) {
        return userEditService.enabled(userid);
    }

    @ResponseBody
    @GetMapping("book-listadmin")
    public Page<Book> getAllBook(@RequestHeader int page, @RequestHeader int pageSize,
                                 @RequestHeader String sortField, @RequestHeader String sortOrder) {
        Sort sort = Sort.by(sortField).ascending();
        if(sortOrder == "des") {
            sort.descending();
        }

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return bookRepository.findAll(pageable);
    }

    @ResponseBody
    @GetMapping("reportListAdmin")
    public Page<Report> getPageReport(@RequestHeader int page, @RequestHeader int pageSize,
                                @RequestHeader String sortField, @RequestHeader String sortOrder) {
        Sort sort = Sort.by(sortField).ascending();
        if(sortOrder == "des") {
            sort.descending();
        }

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return reportRepository.findAll(pageable);
    }

    @ResponseBody
    @GetMapping(value="book-viewadmin")
    public Book getBookAdmin(@RequestHeader int bookid) {
        return bookRepository.findById(bookid);
    }

    @ResponseBody
    @GetMapping(value="book-enabledadmin")
    public boolean enabledBook(@RequestHeader int bookid) {
        return bookEditService.enabled(bookid
        );
    }

}
