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
            var user =  bookRepository.findById(bookid);
            if (user.getEnabled() == false){
                user.setEnabled(true);
            }
            else {
                user.setEnabled(false);
            }
            bookRepository.save(user);
            return true;
        }
        catch (Exception e) {
            System.out.println("lỗi");
        }
        return false;
    }
}
