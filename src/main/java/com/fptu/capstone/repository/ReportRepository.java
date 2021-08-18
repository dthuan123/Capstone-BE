package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findAll();
    Report findById(int id);
    List<Report> findAllByUserReceiverId(int id);
    @Query("SELECT r FROM Report r JOIN r.userSender s WHERE  r.userSender.name like %?1%")
    Page<Report> findByUserSenderName(String name, Pageable pageable);
    Page<Report> findByUserSenderId(int id, Pageable pageable);
    Page<Report> findByUserSender_Name(String name, Pageable pageable);
    Page<Report> findALlByUserSenderIdAndReportContentContainsOrderByReportedDateDesc(int id, Pageable pageable, String searchKeyword);
    Page<Report> findByUserSenderIdOrderByReportedDateDesc(int id, Pageable pageable);
}
