package com.kyd.springbootkyd.controller;

import com.kyd.springbootkyd.pojo.User;
import com.kyd.springbootkyd.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/home"})
public class UserController {
    @Autowired
    private UserService userService;

    public UserController() {
    }

    @PostMapping({"/getuserId"})
    public ResponseEntity<Map<String, String>> createUser() {
        try {
            String generatedUserId = this.userService.createUser();
            return ResponseEntity.ok(Map.of("userId", generatedUserId));
        } catch (Exception var2) {
            return ResponseEntity.status(500).body(Map.of("error", "Error creating user"));
        }
    }

    @PostMapping({"/getuserContent"})
    public ResponseEntity<String> submitSurvey(@RequestBody User user) {
        try {
            this.userService.saveUserSurvey(user);
            return ResponseEntity.ok("问卷提交成功！");
        } catch (Exception var3) {
            return ResponseEntity.status(500).body("问卷提交失败");
        }
    }
}
