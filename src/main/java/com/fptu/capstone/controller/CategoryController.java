package com.fptu.capstone.controller;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Category;
import com.fptu.capstone.repository.BookRepository;
import com.fptu.capstone.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @ResponseBody
    @GetMapping("/category-list")
    public List<Category> listAllCategory(){
        return categoryRepository.findAll();
    }

    @ResponseBody
    @GetMapping("/book-list")
    public Page<Book> listBooks(@RequestHeader int categoryId, @RequestHeader int page, @RequestHeader int pageSize,
                                @RequestHeader String sortField, @RequestHeader String sortOrder) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return bookRepository.findBookByEnabledAndCategoriesId(true, categoryId, pageable);
    }
}
