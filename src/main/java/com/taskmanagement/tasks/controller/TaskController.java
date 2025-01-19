package com.taskmanagement.tasks.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagement.dto.ApiResponse;
import com.taskmanagement.tasks.model.Task;
import com.taskmanagement.tasks.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tasks")
@Validated  // 將驗證應用到整個控制器
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }
    
    // 建立任務
    @PostMapping("/user/{id}")
    public ResponseEntity<ApiResponse> createTask(@Valid @RequestBody Task task, @PathVariable("id") Long userId) {
        
        ApiResponse response = service.createTask(task, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 查詢任務
    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse> getTaskById(@PathVariable Integer taskId) {

        ApiResponse response = service.getTaskById(taskId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // 查詢特定用戶的所有任務
    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<List<Task>>> getAllTasks(@PathVariable("id") Long userId) {

        List<Task> tasks = service.getAllTasks(userId);
        ApiResponse<List<Task>> response = new ApiResponse<>("Tasks retrieved successfully", tasks);
        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    // 更新任務
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTask(@PathVariable Integer id, @Valid @RequestBody Task task) {
 
        ApiResponse response = service.updateTask(task, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 刪除任務
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
  
        service.deleteTask(id);
        return ResponseEntity.noContent().build(); // 返回 204 No Content 狀態碼


    }

    // 完成任務
    @PatchMapping("/{id}/task-done")
    public ResponseEntity<ApiResponse> completedTodo(@PathVariable Long id) {

        ApiResponse response = service.markTaskAsCompleted(id);
        return ResponseEntity.ok(response);
    }

    // 任務設為未完成
    @PatchMapping("/{id}/task-pending")
    public ResponseEntity<ApiResponse> inCompletedTodo(@PathVariable Long id){

        ApiResponse response = service.markTaskAsPending(id);
        return ResponseEntity.ok(response);

    }
}