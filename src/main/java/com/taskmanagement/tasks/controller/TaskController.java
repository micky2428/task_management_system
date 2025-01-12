//取名最好和資料夾路徑相符
package com.taskmanagement.tasks.controller;
//為了用List
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taskmanagement.tasks.model.Task;

@Controller
@RequestMapping("/products") // This means all URLs start with http://localhost:8080/products/
public class TaskController {
    //引用product.java裡面的product class
    private List<Task> productsList = List.of(
        new Task(1, "Rice", 1.00),
        new Task(2, "Dumpling", 3.50),
        new Task(3, "Soup", 2.00)

    );

    @RequestMapping("/") // This maps to the URL http://localhost:8080/products/
    @ResponseBody
    public String home() {
        return "Welcome to Resturant!";
    }

    @RequestMapping("/list") // This maps to the URL http://localhost:8080/products/list
    public String listProducts(Model productListModel) { // Model argument is used to pass data to the view
        productListModel.addAttribute("products", productsList); // Add the productsList to the model
        return "menu";  // This returns the view name, that is, the HTML file name
    }

    @RequestMapping("/details/{id}") // This maps to the URL http://localhost:8080/products/details/{id}
    @ResponseBody
    public String getProductDetailsByID(@PathVariable int id){
        for (Task product : productsList) {
            if (product.getId() == id) {
                return "<strong>Requested Product Details: </strong> <hr> Product ID: " + product.getId() + "<br> Name: " + product.getName() + "<br> Price: $" + product.getPrice();
            }
        }
        return "Product not found!";
    }
}


package com.taskmanagement.tasks.controller;


import jakarta.validation.Valid;
import com.taskmanagement.dto.ApiResponse;
import com.taskmanagement.model.Task;
import com.taskmanagement.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

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
    public ResponseEntity<ApiResponse> getAllTasks(@PathVariable("id") Long userId) {

        List<Task> tasks = service.getAllTasks(userId);
        ApiResponse response = new ApiResponse("Tasks retrieved successfully", tasks);
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

        ApiResponse response = service.doneTask(id);
        return ResponseEntity.ok(response);
    }

    // 任務設為未完成
    @PatchMapping("/{id}/task-pending")
    public ResponseEntity<ApiResponse> inCompletedTodo(@PathVariable Long id){

        ApiResponse response = service.pendingTask(id);
        return ResponseEntity.ok(response);

    }
}