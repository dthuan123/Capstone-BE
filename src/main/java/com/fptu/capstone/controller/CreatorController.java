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
@RequestMapping("creator")
public class CreatorController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    AliasRepository aliasRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookStatusRepository bookStatusRepository;

    @Autowired
    ChapterCommentRepository chapterCommentRepository;

    private static String imageBaseURL = "http://localhost:8000/content/images/books/";


    @ResponseBody
    @GetMapping("get/books")
    public Page<Book> getBookListByCreator(@RequestHeader int creatorId, @RequestHeader String searchKeyword,
                                           @RequestHeader int page, @RequestHeader int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        if(!searchKeyword.equals("")) {
            return bookRepository.findALlByCreatorIdAndNameContains(creatorId, searchKeyword, pageable);
        }

        return bookRepository.findALlByCreatorId(creatorId, pageable);
    }

    @ResponseBody
    @GetMapping(value="get/book")
    public Book getBook(@RequestHeader int bookId) {
        return bookRepository.findById(bookId);
    }

    @ResponseBody
    @GetMapping(value="get/chapter")
    public Chapter getChapter(@RequestHeader int chapterId) {
        return chapterRepository.findById(chapterId);
    }

    @ResponseBody
    @PostMapping(value="create/book", consumes = {   "multipart/form-data" })
    public boolean createBook(@RequestPart("book") Book book, @RequestPart(value = "coverImage", required=false) MultipartFile coverImage) {
        book.setEnabled(true);
        book.setStartedDate(new Date());
        book.setUpdatedDate(new Date());

        BookStatus bookStatus = new BookStatus();
        bookStatus.setId(1);
        book.setBookStatus(bookStatus);

        Book savedBook = bookRepository.save(book);
        try {
            if(coverImage != null) {
                byte[] bytes = coverImage.getBytes();
                String fileName = coverImage.getOriginalFilename();
                String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                fileName = savedBook.getId() + "." + extension;

                savedBook.setImageLink(imageBaseURL + fileName);
                bookRepository.save(savedBook);
                BufferedOutputStream bf = new BufferedOutputStream(new FileOutputStream(new File(
                        "src/main/content/images/books/" + fileName
                )));
                bf.write(bytes);
                bf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
    @PostMapping(value="update/book", consumes = {   "multipart/form-data" })
    public boolean updateBook(@RequestPart("book") Book book, @RequestPart(value = "coverImage", required=false) MultipartFile coverImage) {
        book.setUpdatedDate(new Date());
        Book savedBook = bookRepository.save(book);

        try {
            if(coverImage != null) {
                byte[] bytes = coverImage.getBytes();
                String fileName = coverImage.getOriginalFilename();
                String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                fileName = savedBook.getId() + "." + extension;

                savedBook.setImageLink(imageBaseURL + fileName);
                bookRepository.save(savedBook);
                BufferedOutputStream bf = new BufferedOutputStream(new FileOutputStream(new File(
                        "src/main/content/images/books/" + fileName
                )));
                bf.write(bytes);
                bf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @ResponseBody
    @PostMapping(value="create/alias")
    public ResponseEntity createAlias(@RequestBody Alias alias) {
        if(aliasRepository.findByName(alias.getName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            aliasRepository.save(alias);
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @ResponseBody
    @PostMapping(value="create/chapter")
    public ResponseEntity createChapter(@RequestBody Chapter chapter) {
        chapter.setStartedDate(new Date());
        chapterRepository.save(chapter);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @ResponseBody
    @PostMapping(value="edit/chapter")
    public ResponseEntity editChapter(@RequestBody Chapter chapter) {
        Chapter updateChapter = chapterRepository.findById(chapter.getId());
        updateChapter.setUpdatedDate(new Date());
        updateChapter.setName(chapter.getName());
        updateChapter.setContent(chapter.getContent());
        chapterRepository.save(updateChapter);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @ResponseBody
    @DeleteMapping(value="delete/chapter")
    public ResponseEntity deleteChapter(@RequestHeader int chapterId) {
        chapterRepository.deleteById(chapterId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @ResponseBody
    @GetMapping(value="get/chapters")
    public Page<Chapter> getChapter(@RequestHeader int bookId, @RequestHeader String searchKeyword,
                                    @RequestHeader int page, @RequestHeader int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        if(!searchKeyword.equals("")) {
            return chapterRepository.findALlByBookIdAndNameContains(bookId, searchKeyword, pageable);
        }

        return chapterRepository.findChapterByBookId(bookId, pageable);
    }

    @ResponseBody
    @GetMapping(value="get/aliases")
    public List<Alias> getAliases(@RequestHeader int creatorId) {
        return aliasRepository.findAliasByUserId(creatorId);
    }

    @ResponseBody
    @GetMapping(value="get/categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @ResponseBody
    @GetMapping(value="get/bookStatuses")
    public List<BookStatus> getBookStatuses() {
        return bookStatusRepository.findAll();
    }

    @ResponseBody
    @PostMapping(value="create/comment")
    public ResponseEntity addComment(@RequestBody ChapterComment comment) {
        chapterCommentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @ResponseBody
    @GetMapping(value="account/seeInfo")
    public User seeAccountInformation(@RequestHeader int userId) {
        User user = userRepository.findById(userId).get(0);
        return user;
    }

}
