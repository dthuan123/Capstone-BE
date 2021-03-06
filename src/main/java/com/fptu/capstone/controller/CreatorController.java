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
import java.net.URLDecoder;
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

    private static String imageBaseURL = "http://localhost:8000/content/images/books/";


    @ResponseBody
    @GetMapping("get/books")
    public Page<Book> getBookListByCreator(@RequestHeader int creatorId, @RequestHeader String searchKeyword,
                                           @RequestHeader int page, @RequestHeader int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        searchKeyword = URLDecoder.decode(searchKeyword);
        if(!searchKeyword.equals("")) {
            return bookRepository.findALlByCreatorIdAndNameContains(creatorId, searchKeyword, pageable);
        }

        return bookRepository.findALlByCreatorId(creatorId, pageable);
    }

    @ResponseBody
    @GetMapping(value="/get/book")
    public Book getBook(@RequestHeader int bookId) {
        return bookRepository.findById(bookId);
    }

    @ResponseBody
    @GetMapping(value="get/chapter")
    public Chapter getChapter(@RequestHeader int chapterId) {
        return chapterRepository.findById(chapterId);
    }

    @ResponseBody
    @GetMapping(value="get/next-chapter")
    public int getNextChapter(@RequestHeader int chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId);
        Book book = chapter.getBook();
        List<Chapter> chapters = chapterRepository.findByBookIdAndChapterStatusId(book.getId(), 2);

        for (Chapter nextChapter: chapters
             ) {
            if(nextChapter.getId()>chapterId){
                return nextChapter.getId();
            }
        }
        return 0;
    }

    @ResponseBody
    @GetMapping(value="get/pre-chapter")
    public int getPreChapter(@RequestHeader int chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId);
        Book book = chapter.getBook();
        List<Chapter> chapters = chapterRepository.findByBookIdAndChapterStatusId(book.getId(), 2);

        for(int i=0; i<chapters.size(); i++){
            if(chapters.get(0).getId()==chapterId){
                return 0;
            }
            else if(chapters.get(i).getId()==chapterId){
                return chapters.get(i-1).getId();
            }
        }
        return 0;
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
        book.setImageLink("http://localhost:8000/content/images/books/anhdefaul.png");

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
        ChapterStatus status = new ChapterStatus();
        status.setId(1);
        chapter.setChapterStatus(status);
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
        searchKeyword = URLDecoder.decode(searchKeyword);
        if(!searchKeyword.equals("")) {
            return chapterRepository.findALlByBookIdAndNameContains(bookId, searchKeyword, pageable);
        }

        return chapterRepository.findChapterByBookId(bookId, pageable);
    }

    @ResponseBody
    @GetMapping(value="publish")
    public ResponseEntity publishChapter(@RequestHeader Date publishDate, @RequestHeader int chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId);
        chapter.setPublishDate(publishDate);
        chapterRepository.save(chapter);
        return ResponseEntity.status(HttpStatus.OK).body(null);
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


}
