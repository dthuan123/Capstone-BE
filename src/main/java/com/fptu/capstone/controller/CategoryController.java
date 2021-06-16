package com.fptu.capstone.controller;

import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Category;
import com.fptu.capstone.repository.BookRepository;
import com.fptu.capstone.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Book> listBooks(@RequestHeader int categoryId){
        return bookRepository.findByCategoriesId(categoryId);
    }
}
