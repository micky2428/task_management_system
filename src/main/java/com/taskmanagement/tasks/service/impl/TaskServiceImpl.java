package com.taskmanagement.tasks.service.impl;


import com.taskmanagement.dto.ApiResponse;
import com.taskmanagement.exception.ResourceNotFoundException;
import com.taskmanagement.tasks.model.Task;
import com.taskmanagement.auth.model.User;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.taskmanagement.tasks.service.TaskService;

import java.util.List;

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
    public ApiResponse getAllTasks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId + " not found"));
        List<Task> taskList = taskRepository.findAllByUserId(user.getId());
        return new ApiResponse("Tasks retrieved successfully", taskList);
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
    public ApiResponse deleteTask(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID: " + id + " not found"));
        taskRepository.delete(task);
        return new ApiResponse("Task deleted successfully", null); // Returning null as there's no task to return
    }

    @Override
    public ApiResponse markTaskAsCompleted(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID: " + id + " not found"));
        task.setCompleted(true);
        Task updatedTask = taskRepository.save(task);
        return new ApiResponse("Task marked as done", updatedTask);
    }

    @Override
    public ApiResponse markTaskAsPending(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID: " + id + " not found"));
        task.setCompleted(false);
        Task updatedTask = taskRepository.save(task);
        return new ApiResponse("Task marked as pending", updatedTask);
    }
}