package com.taskmanagement.tasks.service;

import java.util.List;

import com.taskmanagement.dto.ApiResponse;
import com.taskmanagement.tasks.model.Task;

//提供基本的 CRUD 操作
//List<Task>（建議僅用於查詢多個任務）
//ApiResponse（適用於需要回傳訊息或錯誤資訊的情境）

public interface TaskService {
    //創建 Task 需要返回成功訊息
    ApiResponse createTask(Task task, Long userId);
    //查詢單一 Task，回傳 Task 物件
    ApiResponse getTaskById(Integer taskId);
    //查詢多個 Task，回傳 List<Task>
    List<Task> getAllTasks(Long userId);
    // 更新 Task 回傳成功訊息
    ApiResponse updateTask(Task task, Integer id);
    // 刪除 Task 回傳成功訊息
    ApiResponse deleteTask(Long id);
    // 改變 Task 狀態（完成或待處理）回傳成功訊息
    ApiResponse markTaskAsCompleted(Long id);

    ApiResponse markTaskAsPending(Long id);
}