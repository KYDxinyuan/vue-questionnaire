
package com.kyd.springbootkyd.service;

import com.kyd.springbootkyd.pojo.User;
import com.kyd.springbootkyd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String createUser() {
        String userId = UUID.randomUUID().toString();
        User user = new User();
        user.setUserId(userId);
        userRepository.save(user);
        return userId;
    }

    public void saveUserSurvey(User user) {
        User existingUser = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setSelectedOption(user.getSelectedOption());
        existingUser.setSelectedOptionText(user.getSelectedOptionText());
        userRepository.save(existingUser);
    }

    public List<User> getUsersByNameWithPagination(String name, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        if (name != null && !name.isEmpty()) {
            return userRepository.findByUserIdContaining(name, pageable).getContent();
        }
        return userRepository.findAll(pageable).getContent();
    }

    public long getTotalCount(String name) {
        if (name != null && !name.isEmpty()) {
            return userRepository.countByUserIdContaining(name);
        }
        return userRepository.count();
    }

    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }



    public List<User> getUsersByFiltersWithPagination(String selectedOption, String selectedOptionText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        if ((selectedOption != null && !selectedOption.isEmpty()) || (selectedOptionText != null && !selectedOptionText.isEmpty())) {
            return userRepository
                    .findBySelectedOptionContainingOrSelectedOptionTextContaining(selectedOption, selectedOptionText, pageable)
                    .getContent();
        }
        return userRepository.findAll(pageable).getContent();
    }
//
//    public long getTotalCount(String selectedOption, String selectedOptionText) {
//        if ((selectedOption != null && !selectedOption.isEmpty()) || (selectedOptionText != null && !selectedOptionText.isEmpty())) {
//            return userRepository.countBySelectedOptionContainingOrSelectedOptionTextContaining(selectedOption, selectedOptionText);
//        }
//        return userRepository.count();
//    }
public long getTotalCount(String selectedOption, String selectedOptionText) {
    // 截取前10个字符
    if (selectedOption != null && !selectedOption.isEmpty()) {
        selectedOption = selectedOption.substring(0, Math.min(selectedOption.length(), 10));
    }
    if (selectedOptionText != null && !selectedOptionText.isEmpty()) {
        selectedOptionText = selectedOptionText.substring(0, Math.min(selectedOptionText.length(), 10));
    }

    if ((selectedOption != null && !selectedOption.isEmpty()) || (selectedOptionText != null && !selectedOptionText.isEmpty())) {
        return userRepository.countBySelectedOptionContainingOrSelectedOptionTextContaining(selectedOption, selectedOptionText);
    }
    return userRepository.count();
}
}
