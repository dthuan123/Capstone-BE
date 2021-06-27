package com.fptu.capstone.controller;

import com.fptu.capstone.entity.Report;
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
@RequestMapping("report")
public class ReportController {
    @Autowired
    ReportRepository reportRepository;

    @ResponseBody
    @GetMapping("report-list")
    public Page<Report> getPage(@RequestHeader int page, @RequestHeader int pageSize,
                               @RequestHeader String sortField, @RequestHeader String sortOrder) {
        Sort sort = Sort.by(sortField).ascending();
        if(sortOrder == "des") {
            sort.descending();
        }

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return reportRepository.findAll(pageable);
    }

    @ResponseBody
    @GetMapping("report-all")
    public List<Report> getAll() {
        return reportRepository.findAll();
    }
}
