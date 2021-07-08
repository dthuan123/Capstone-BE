package com.fptu.capstone.service;

import com.fptu.capstone.repository.BookRepository;
import com.fptu.capstone.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BookEditServicelmpl implements BookEditService{

    private final BookRepository bookRepository;

    public BookEditServicelmpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }



    @Override
    public boolean enabled(int bookid) {
        try {
            var book =  bookRepository.findById(bookid);
            if (book.getEnabled() == false){
                book.setEnabled(true);
            }
            else {
                book.setEnabled(false);
            }
            bookRepository.save(book);
            return true;
        }
        catch (Exception e) {
            System.out.println("lỗi");
        }
        return false;
    }
}
