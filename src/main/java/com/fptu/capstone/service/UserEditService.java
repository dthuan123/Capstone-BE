package com.fptu.capstone.service;

import org.springframework.stereotype.Service;

@Service
public interface UserEditService  {
    boolean approved(int id);
    boolean enabled(int id);
}
