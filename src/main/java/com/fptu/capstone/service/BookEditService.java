package com.fptu.capstone.service;

import org.springframework.stereotype.Service;

@Service
public interface BookEditService {
    boolean enabled(int bookid);
}
