
package com.kyd.springbootkyd.controller;

import com.kyd.springbootkyd.pojo.User;
import com.kyd.springbootkyd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/home")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController() {}

    @PostMapping("/getuserId")
    public ResponseEntity<Map<String, String>> createUser() {
        try {
            String generatedUserId = userService.createUser();
            return ResponseEntity.ok(Map.of("userId", generatedUserId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error creating user"));
        }
    }

    @PostMapping("/getuserContent")
    public ResponseEntity<String> submitSurvey(@RequestBody User user) {
        try {
            userService.saveUserSurvey(user);
            return ResponseEntity.ok("问卷提交成功！");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("问卷提交失败");
        }
    }

//    @GetMapping("/getUserContent")
//    public ResponseEntity<Object> getUserContent(
//            @RequestParam(value = "name", required = false) String name,
//            @RequestParam(value = "page", defaultValue = "1") int page,
//            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        try {
//            List<User> users = userService.getUsersByNameWithPagination(name, page, pageSize);
//            long total = userService.getTotalCount(name);
//            return ResponseEntity.ok(Map.of(
//                    "code", 200,
//                    "data", Map.of(
//                            "list", users,
//                            "total", total
//                    )
//            ));
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(Map.of("code", 500, "message", "Error fetching users"));
//        }
//    }
@GetMapping("/getUserContent")
public ResponseEntity<Object> getUserContent(
        @RequestParam(value = "selectedOption", required = false) String selectedOption,
        @RequestParam(value = "selectedOptionText", required = false) String selectedOptionText,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    try {
        List<User> users = userService.getUsersByFiltersWithPagination(selectedOption, selectedOptionText, page, pageSize);
        long total = userService.getTotalCount(selectedOption, selectedOptionText);
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "data", Map.of(
                        "list", users,
                        "total", total
                )
        ));
    } catch (Exception e) {
        return ResponseEntity.status(500).body(Map.of("code", 500, "message", "Error fetching users"));
    }
}

    @DeleteMapping("/UserContent")
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestParam Long id) {
        try {
            boolean deleted = userService.deleteUserById(id);
            if (deleted) {
                return ResponseEntity.ok(Map.of(
                        "code", 200,
                        "message", "用户删除成功"
                ));
            } else {
                return ResponseEntity.status(404).body(Map.of(
                        "code", 404,
                        "message", "用户未找到"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "code", 500,
                    "message", "删除用户时出错"
            ));
        }
    }
}
