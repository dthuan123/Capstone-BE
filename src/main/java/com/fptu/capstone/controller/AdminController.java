package com.fptu.capstone.controller;


import com.fptu.capstone.entity.*;
import com.fptu.capstone.repository.BookRepository;
import com.fptu.capstone.repository.CategoryRepository;
import com.fptu.capstone.repository.ReportRepository;
import com.fptu.capstone.repository.UserRepository;
import com.fptu.capstone.service.BookEditService;
import com.fptu.capstone.service.ReportEditService;
import com.fptu.capstone.service.UserEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    CategoryRepository categoryRepository;

    final UserRepository userRepository;
    final ReportRepository reportRepository;
    final BookRepository bookRepository;
    private final UserEditService userEditService;
    private final BookEditService bookEditService;
    private final ReportEditService reportEditService;

    public AdminController(UserRepository userRepository, ReportRepository reportRepository, BookRepository bookRepository, UserEditService userEditService, BookEditService bookEditService, ReportEditService reportEditService) {
        this.userEditService = userEditService;
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
        this.bookRepository = bookRepository;
        this.bookEditService = bookEditService;
        this.reportEditService = reportEditService;
    }

    @ResponseBody
    @PostMapping("user-list")
    public Page<User> getAll(@RequestBody SearchBook search) {
        Sort sort = Sort.by(search.getSortField()).ascending();
        if(search.getSortOrder() == "des") {
            sort.descending();
        }

        Pageable pageable = PageRequest.of(search.getPage(), search.getPageSize(), sort);
        if(!search.getSearchKeyword().equals("")) {
            return userRepository.findAllByName(search.getSearchKeyword(),pageable);
        }
        if(search.getUserid() != -1) {
            return userRepository.findAllById(search.getUserid(),pageable);
        }
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
    @PostMapping("book-listadmin")
    public Page<Book> getAllBook(@RequestBody SearchBook search) {
        Sort sort = Sort.by(search.getSortField()).ascending();
        if(search.getSortOrder() == "des") {
            sort.descending();
        }

        Pageable pageable = PageRequest.of(search.getPage(), search.getPageSize(), sort);
        if(!search.getSearchKeyword().equals("")) {
            var Booklist = bookRepository.findAllByName(search.getSearchKeyword(),pageable);
            return Booklist;
        }
        return bookRepository.findAll(pageable);
    }

    @ResponseBody
    @PostMapping("reportListAdmin")
    public Page<Report> getPageReport(@RequestBody SearchBook search) {
        Sort sort = Sort.by(search.getSortField()).ascending();
        if(search.getSortOrder() == "des") {
            sort.descending();
        }

        Pageable pageable = PageRequest.of(search.getPage(), search.getPageSize(), sort);
        if(!search.getSearchKeyword().equals("")) {
            var Reportlist = reportRepository.findByUserSenderName(search.getSearchKeyword(),pageable);
            return Reportlist;
        }
        return reportRepository.findAll(pageable);
    }

    @ResponseBody
    @GetMapping(value="report-viewadmin")
    public Report getReportAdmin(@RequestHeader int reportid) {
        return reportRepository.findById(reportid);
    }

    @ResponseBody
    @PostMapping(value="report-responseadmin")
    public boolean responseadmin(@RequestBody Report report) {
        return reportEditService.response(report.getResponseContent(),report.getId());
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

    @ResponseBody
    @GetMapping(value="categories")
    public Page<Category> getCategories(@RequestHeader int page, @RequestHeader int pageSize, @RequestHeader String searchKeyword) {
        Pageable pageable = PageRequest.of(page, pageSize);
        searchKeyword = URLDecoder.decode(searchKeyword);
        if(!searchKeyword.equals("")) {
            return categoryRepository.findAllByNameContains(searchKeyword, pageable);
        }
        return categoryRepository.findAll(pageable);
    }

    @ResponseBody
    @PostMapping(value="categories")
    public ResponseEntity addCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }



}