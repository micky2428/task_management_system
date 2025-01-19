package com.taskmanagement.tasks.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.taskmanagement.auth.model.User;
import com.taskmanagement.dto.ApiResponse;
import com.taskmanagement.exception.ResourceNotFoundException;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import com.taskmanagement.tasks.model.Task;
import com.taskmanagement.tasks.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ApiResponse createTask(Task task, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return new ApiResponse("Task created successfully", savedTask);
    }

    @Override
    public ApiResponse getTaskById(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID: " + taskId + " not found"));
        return new ApiResponse("Task found", task);
    }

    @Override
    public List<Task> getAllTasks(Long userId) {
        // 查詢 User 並處理錯誤
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId + " not found"));
        // 查詢 User 的所有任務
        return taskRepository.findAllByUser_Id(user.getId());
    }

    @Override
    public ApiResponse updateTask(Task task, Integer id) {
        Task foundTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID: " + id + " not found"));

        foundTask.setTask(task.getTask());
        foundTask.setCompleted(task.getCompleted());
        foundTask.setDetails(task.getDetails());

        Task updatedTask = taskRepository.save(foundTask);
        return new ApiResponse("Task updated successfully", updatedTask);
    }

    @Override
    public ApiResponse deleteTask(Long id) {
        List<Task> tasks = taskRepository.findAllByUser_Id(id);  // 查找某個使用者的所有任務
    
        if (tasks.isEmpty()) {
            throw new ResourceNotFoundException("Task with ID: " + id + " not found");
        }
    
        Task taskToDelete = tasks.get(0);  // 假設刪除第一個找到的任務
        taskRepository.delete(taskToDelete);  // 刪除該任務
    
        return new ApiResponse("Task deleted successfully", null);  // 返回成功訊息
    }

    @Override
    public ApiResponse markTaskAsCompleted(Long id) {
        List<Task> tasks = taskRepository.findAllByUser_Id(id);  // 查找某個使用者的所有任務
    
        if (tasks.isEmpty()) {
            throw new ResourceNotFoundException("Task with ID: " + id + " not found");
        }
    
        Task taskToUpdate = tasks.get(0);  // 假設更新第一個找到的任務
        taskToUpdate.setCompleted(true);  // 設為完成
        Task updatedTask = taskRepository.save(taskToUpdate);  // 保存更新
    
        return new ApiResponse("Task marked as done", updatedTask);  // 返回更新後的任務
    }

    @Override
    public ApiResponse markTaskAsPending(Long id) {
        List<Task> tasks = taskRepository.findAllByUser_Id(id);  // 查找某個使用者的所有任務
    
        if (tasks.isEmpty()) {
            throw new ResourceNotFoundException("Task with ID: " + id + " not found");
        }
    
        Task taskToUpdate = tasks.get(0);  // 假設更新第一個找到的任務
        taskToUpdate.setCompleted(false);  // 設為未完成
        Task updatedTask = taskRepository.save(taskToUpdate);  // 保存更新
    
        return new ApiResponse("Task marked as pending", updatedTask);  // 返回更新後的任務
    }
}