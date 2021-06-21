package com.fptu.capstone.controller;

import com.fptu.capstone.entity.*;
import com.fptu.capstone.repository.AliasRepository;
import com.fptu.capstone.repository.BookRepository;
import com.fptu.capstone.repository.CategoryRepository;
import com.fptu.capstone.repository.ChapterRepository;
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
    ChapterRepository chapterRepository;

    @Autowired
    AliasRepository aliasRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private static String imageBaseURL = "http://localhost:8000/content/images/books/";


    @ResponseBody
    @GetMapping("get/books")
    public Page<Book> getBookListByCreator(@RequestHeader int page, @RequestHeader int pageSize,
                                           @RequestHeader String sortField, @RequestHeader String sortOrder) {
        Sort sort = Sort.by(sortField).ascending();
        if(sortOrder == "des") {
            sort.descending();
        }

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return bookRepository.findAll(pageable);
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
    @GetMapping(value="get/chapters")
    public Page<Chapter> getChapter(@RequestHeader int page, @RequestHeader int pageSize,
                                    @RequestHeader String sortField, @RequestHeader String sortOrder,
                                    @RequestHeader int bookId) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return chapterRepository.findChapterByBookId(bookId, pageable);
    }
//
//    @ResponseBody
//    @DeleteMapping(value="get/chapters")
//    public List<Chapter> deleteChapter(@RequestHeader int chapterId) {
//        return chapterRepository.delete(chapterId);
//    }

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
}
