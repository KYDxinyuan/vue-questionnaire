package com.kyd.springbootkyd.repository;

import com.kyd.springbootkyd.pojo.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
}
