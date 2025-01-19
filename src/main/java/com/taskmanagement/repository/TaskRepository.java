package com.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.taskmanagement.tasks.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    //根據用戶的 id 查詢所有與該用戶關聯的 Task
    List<Task> findAllByUser_Id(Long userId); // 清晰地表明是通過外鍵查詢
    Page<Task> findAllByUser_Id(Long userId, Pageable pageable); // 支持分頁
}

// TaskRepository:JPA Repository 接口，專門負責對 Task 實體的數據庫操作