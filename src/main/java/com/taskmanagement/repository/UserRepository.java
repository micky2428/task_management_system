package com.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.taskmanagement.auth.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //根據用戶名查找用戶。
    Optional<User> findByUsername(String username);
    //根據用戶名或電子郵件查找用戶
    Optional<User> findByUsernameOrEmail(String username, String email);
    //判斷某個電子郵件是否已存在於數據庫中
    Boolean existsByEmail(String email);
    //判斷某個用戶名是否已存在於數據庫中
    Boolean existsByUsername(String username);
}

//Optional 是一種包裝類型，用於表示一個值可能存在，也可能不存在的情況，好處避免 null 值帶來的問題