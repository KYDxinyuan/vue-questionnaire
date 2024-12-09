

package com.kyd.springbootkyd.repository;

import com.kyd.springbootkyd.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    Page<User> findByUserIdContaining(String userId, Pageable pageable);

    long countByUserIdContaining(String userId);


    Page<User> findBySelectedOptionContainingOrSelectedOptionTextContaining(String selectedOption, String selectedOptionText, Pageable pageable);

    long countBySelectedOptionContainingOrSelectedOptionTextContaining(String selectedOption, String selectedOptionText);

}


