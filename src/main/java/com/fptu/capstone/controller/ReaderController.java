package com.fptu.capstone.controller;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Report;
import com.fptu.capstone.repository.BookRepository;
import com.fptu.capstone.repository.ChapterRepository;
import com.fptu.capstone.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("reader")
public class ReaderController {

    @Autowired
    ReportRepository reportRepository;

    @ResponseBody
    @GetMapping("message-list")
    public Page<Report> getMessageListByReaderId(@RequestHeader int page, @RequestHeader int pageSize, @RequestHeader int userId) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return reportRepository.findByUserSenderId(userId, pageable);
    }

}
