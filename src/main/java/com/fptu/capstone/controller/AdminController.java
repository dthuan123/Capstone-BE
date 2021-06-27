package com.fptu.capstone.controller;


import com.fptu.capstone.entity.Chapter;
import com.fptu.capstone.entity.Report;
import com.fptu.capstone.entity.User;
import com.fptu.capstone.repository.ReportRepository;
import com.fptu.capstone.repository.UserRepository;
import com.fptu.capstone.service.UserEditService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    UserRepository userRepository;
    ReportRepository reportRepository;
    private final UserEditService userEditService;

    public AdminController(UserEditService userEditService) {
        this.userEditService = userEditService;
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
    @GetMapping("report-all")
    public List<Report> getAll() {
        return reportRepository.findAll();
    }

}
