package com.kyd.springbootkyd.service;

import com.kyd.springbootkyd.pojo.User;
import com.kyd.springbootkyd.repository.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService() {
    }

    public String createUser() {
        String userId = UUID.randomUUID().toString();
        User user = new User();
        user.setUserId(userId);
        this.userRepository.save(user);
        return userId;
    }

    public void saveUserSurvey(User user) {
        User existingUser = (User)this.userRepository.findByUserId(user.getUserId()).orElseThrow(() -> {
            return new RuntimeException("User not found");
        });
        existingUser.setSelectedOption(user.getSelectedOption());
        existingUser.setSelectedOptionText(user.getSelectedOptionText());
        this.userRepository.save(existingUser);
    }
}