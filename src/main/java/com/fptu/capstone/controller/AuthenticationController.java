package com.fptu.capstone.controller;

import com.fptu.capstone.entity.User;
import com.fptu.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        List<User> users = userRepository.findByName(user.getName());
        if(users.size() == 1) {
            return ResponseEntity.ok(users.get(0));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
