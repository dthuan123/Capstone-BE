package com.fptu.capstone.controller;

import com.fptu.capstone.entity.*;
import com.fptu.capstone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("reader")
public class ReaderController {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepository userRepository;

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

    @ResponseBody
    @PostMapping(value="create-report")
    public ResponseEntity createReport(@RequestBody Report report) {
        report.setReportedDate(new Date());
        reportRepository.save(report);
        return ResponseEntity.status(HttpStatus.OK).body(report);
    }

    @ResponseBody
    @GetMapping(value="account/seeInfo")
    public User seeAccountInformation(@RequestHeader int userId) {
        User user = userRepository.findById(userId).get(0);
        return user;
    }

    @ResponseBody
    @PostMapping(value = "update/avatar")
    public boolean setAvatar(@RequestPart User user, @RequestPart(value = "avatar", required = false) MultipartFile avatar){
        User u = userRepository.getById(user.getId());
        try{
            if(avatar != null){
                byte[] bytes = avatar.getBytes();
                String filename = avatar.getOriginalFilename();
                String extension = filename.substring(filename.lastIndexOf(".")+1);
                //filename = u.getId() + "." + extension;
                u.setAvatarLink("http://localhost:8000/content/images/avatar_images/" + filename);
                userRepository.save(u);
                BufferedOutputStream bff = new BufferedOutputStream(new FileOutputStream(new File(
                        "src/main/content/images/avatar_images/" + filename
                )));
                bff.write(bytes);
                bff.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

}
