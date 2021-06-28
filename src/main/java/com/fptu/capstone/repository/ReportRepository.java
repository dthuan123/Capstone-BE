package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findAll();
    List<Report> findAllByUserReceiverId(int id);
    List<Report> findAllByUserSenderId(int id);
}
