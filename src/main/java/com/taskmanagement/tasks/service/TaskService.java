package com.taskmanagement.tasks.service;

import com.taskmanagement.dto.ApiResponse;
import com.taskmanagement.tasks.model.Task;

//提供基本的 CRUD 操作

public interface TaskService {

    ApiResponse createTask(Task task, Long userId);

    ApiResponse getTaskById(Integer taskId);

    ApiResponse getAllTasks(Long userId);

    ApiResponse updateTask(Task task, Integer id);

    ApiResponse deleteTask(Integer id);

    ApiResponse markTaskAsCompleted(Integer id);

    ApiResponse markTaskAsPending(Integer id);
}