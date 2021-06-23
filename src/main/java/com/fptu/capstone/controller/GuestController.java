package com.fptu.capstone.controller;

import com.fptu.capstone.entity.User;
import com.fptu.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("guess")

public class GuestController {

    @Autowired
    UserRepository userRepository;

    @ResponseBody
    @PostMapping(value="/register")
    public ResponseEntity registerUser(@RequestBody User user){
    userRepository.save(user);
    return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
