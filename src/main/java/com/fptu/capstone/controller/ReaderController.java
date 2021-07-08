package com.fptu.capstone.controller;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Chapter;
import com.fptu.capstone.entity.Report;
import com.fptu.capstone.repository.BookRepository;
import com.fptu.capstone.repository.ChapterRepository;
import com.fptu.capstone.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("reader")
public class ReaderController {

    @Autowired
    ReportRepository reportRepository;

    @ResponseBody
    @GetMapping("message-list")
    public Page<Report> getMessageListByReaderId(@RequestHeader int page, @RequestHeader int pageSize, @RequestHeader int userId, @RequestHeader String searchKeyword) {
        Pageable pageable = PageRequest.of(page, pageSize);
        if(!searchKeyword.equals("")) {
            return reportRepository.findALlByUserSenderIdAndReportContentContains(userId, pageable, searchKeyword);
        }
        return reportRepository.findByUserSenderId(userId, pageable);
    }


    @ResponseBody
    @DeleteMapping(value="delete-message")
    public ResponseEntity deleteMessage(@RequestHeader int reportId) {
        reportRepository.deleteById(reportId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
