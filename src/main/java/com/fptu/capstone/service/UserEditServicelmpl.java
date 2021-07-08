package com.fptu.capstone.service;

import com.fptu.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEditServicelmpl implements UserEditService{

    private final UserRepository userRepository;

    public UserEditServicelmpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean approved(int id) {
        try {
            var user =  userRepository.findById(id).get(0);
            if (user.getApproved() == false){
                user.setApproved(true);
            }
            else {
                user.setApproved(false);
            }
            userRepository.save(user);
            return true;
        }
        catch (Exception e) {
            System.out.println("lỗi");
        }
        return false;
    }

    @Override
    public boolean enabled(int id) {
        try {
            var user =  userRepository.findById(id).get(0);
            if (user.getEnabled() == false){
                user.setEnabled(true);
            }
            else {
                user.setEnabled(false);
            }
            userRepository.save(user);
            return true;
        }
        catch (Exception e) {
            System.out.println("lỗi");
        }
        return false;
    }
}
