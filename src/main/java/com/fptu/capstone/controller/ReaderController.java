package com.fptu.capstone.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fptu.capstone.entity.*;
import com.fptu.capstone.repository.*;
import org.apache.coyote.Response;
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
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ChapterCommentRepository chapterCommentRepository;

    private static String imageBaseURL = "http://localhost:8000/content/images/users/";
    @ResponseBody
    @GetMapping("message-list")
    public Page<Report> getMessageListByReaderId(@RequestHeader int page, @RequestHeader int pageSize, @RequestHeader int userId, @RequestHeader String searchKeyword) {
        Pageable pageable = PageRequest.of(page, pageSize);
        searchKeyword = URLDecoder.decode(searchKeyword);
        if(!searchKeyword.equals("")) {
            return reportRepository.findALlByUserSenderIdAndReportContentContainsOrderByReportedDateDesc(userId, pageable, searchKeyword);
        }
        Page<Report> p = reportRepository.findByUserSenderIdOrderByReportedDateDesc(userId, pageable);
        return reportRepository.findByUserSenderIdOrderByReportedDateDesc(userId, pageable);
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
        Date date = new Date();
        report.setReportedDate(new Date());
        if(!report.getReportContent().isEmpty()) {
            reportRepository.save(report);
        }
        return ResponseEntity.status(HttpStatus.OK).body(report);
    }

    @ResponseBody
    @GetMapping(value="account/seeInfo")
    public User seeAccountInformation(@RequestHeader int userId) {
        try {
            User user = userRepository.findById(userId).get(0);
            return user;
        } catch (NullPointerException e){
            System.out.println(e);
        }
        return null;
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

    @ResponseBody
    @PostMapping(value="history")
    public User saveHistory(@RequestPart("user") User user, @RequestPart("chapter") Chapter chapter) {
        User userInDB = userRepository.findById(user.getId()).get(0);
        List<Chapter> history = userInDB.getHistoryList();
        int index = -1;
        for (int i = 0; i < history.size(); i++) {
            if(history.get(i).getBook().getId() == chapter.getBook().getId()) {
                index = i;
                break;
            }
        }
        if(index != -1) {
            history.set(index, chapter);
        } else {
            history.add(chapter);
        }
        userInDB.setHistoryList(history);

        userRepository.save(userInDB);
        return user;
    }

    @ResponseBody
    @GetMapping(value="get/history")
    public Page<Chapter> getHistory(@RequestHeader int userId, @RequestHeader int page, @RequestHeader int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Chapter> chapters = chapterRepository.findHistoryList(userId, pageable);

        return chapters;
    }

    @ResponseBody
    @GetMapping(value="get/likes")
    public Page<Book> getLikeList(@RequestHeader int userId, @RequestHeader int page, @RequestHeader int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Book> books = bookRepository.findLikeList(userId, pageable);

        return books;
    }

    @ResponseBody
    @PostMapping(value="rate")
    public Book updateBook(@RequestPart Book book, @RequestPart float rating)  {
        int totalRating = book.getTotalRating();
        float oldRating = book.getOverallRating();

        float newRating = ((oldRating * totalRating) + rating) / (totalRating + 1);

        book.setOverallRating(newRating);
        book.setTotalRating(totalRating + 1);
        return bookRepository.save(book);
    }

    @ResponseBody
    @PostMapping(value="comment")
    public ResponseEntity addBookComment(@RequestBody Comment comment) {
        comment.setStartedDate(new Date());
        Book book = bookRepository.findById(comment.getBook().getId());
        comment.setBook(book);
        Comment savedComment = commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.OK).body(savedComment);
    }

    @ResponseBody
    @PostMapping(value="create/comment")
    public ResponseEntity addComment(@RequestBody ChapterComment comment) {
        chapterCommentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @ResponseBody
    @PostMapping(value="apply", consumes = {   "multipart/form-data" })
    public ResponseEntity apply(@RequestPart("card") String card,
                                @RequestPart("userId") int userId,
                                @RequestPart(value = "front", required=false) MultipartFile front,
                                @RequestPart(value = "back", required=false) MultipartFile back){
        card = card.replaceAll("\"", "");
        if(userRepository.existsUserByIdentityCard(card)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card already exists.");
        }

        if(front == null || back == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No image.");
        }

        User user = userRepository.findById(userId).get(0);
        user.setIdentityCard(card);
        try {
            if(front != null && back != null) {
                byte[] bytes = front.getBytes();
                String fileName = front.getOriginalFilename();
                String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                fileName = user.getId() + "front" + "." + extension;
                user.setIdentityCardFront(imageBaseURL + fileName);

                byte[] bytesBack = back.getBytes();
                String fileNameBack = back.getOriginalFilename();
                String extensionBack = fileNameBack.substring(fileNameBack.lastIndexOf(".") + 1);
                fileNameBack = user.getId() + "back" + "." + extensionBack;
                user.setIdentityCardBack(imageBaseURL + fileNameBack);
                Role role = new Role();
                role.setId(2);
                user.setRole(role);
                userRepository.save(user);
                BufferedOutputStream bf = new BufferedOutputStream(new FileOutputStream(new File(
                        "src/main/content/images/users/" + fileName
                )));
                bf.write(bytes);
                bf.close();

                BufferedOutputStream bfBack = new BufferedOutputStream(new FileOutputStream(new File(
                        "src/main/content/images/users/" + fileNameBack
                )));
                bfBack.write(bytesBack);
                bfBack.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
