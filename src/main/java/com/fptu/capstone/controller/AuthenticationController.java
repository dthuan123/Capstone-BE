package com.fptu.capstone.controller;

import com.fptu.capstone.entity.User;
import com.fptu.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @ResponseBody
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User userDB = userRepository.findByNameAndPassword(user.getName(), user.getPassword());
        if(userDB != null) {
            userDB.setPassword(null);
            return userDB;
        }

        return null;
    }
}
