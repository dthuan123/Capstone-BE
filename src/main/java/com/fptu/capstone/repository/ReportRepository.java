package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findAll();
    List<Report> findAllByUserReceiverId(int id);
    Page<Report> findByUserSenderId(int id, Pageable pageable);
}
