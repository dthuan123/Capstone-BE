package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findAll();
    Report findById(int id);
    List<Report> findAllByUserReceiverId(int id);
    Page<Report> findByUserSenderId(int id, Pageable pageable);
    Page<Report> findALlByUserSenderIdAndReportContentContains(int id, Pageable pageable, String searchKeyword);
}
