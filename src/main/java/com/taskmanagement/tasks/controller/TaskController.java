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


// package com.taskmanagement.tasks.controller;


// import jakarta.validation.Valid;
// import taskmanagementsystem.dto.ApiResponse;
// import taskmanagementsystem.model.Task;
// import taskmanagementsystem.service.TaskService;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import java.util.List;

// @RestController
// @RequestMapping("/api/v1/tasks")
// public class TaskController {

//     private final TaskService service;

//     public TaskController(TaskService service) {
//         this.service = service;
//     }

//     @PostMapping("/user/{id}")
//     public ResponseEntity<ApiResponse> createTask(@Valid @RequestBody Task task, @PathVariable("id") Long userId) {
//         return new ResponseEntity<>(service.createTask(task, userId), HttpStatus.CREATED);
//     }

//     @GetMapping("/{taskId}")
//     public ResponseEntity<ApiResponse> getTaskById(@PathVariable Integer taskId) {
//         return new ResponseEntity<>(service.getTaskById(taskId), HttpStatus.OK);
//     }

//     @GetMapping("/user/{id}")
//     public ResponseEntity<List<Task>> getAllTasks(@PathVariable("id") Long userId) {
//         return new ResponseEntity<>(service.getAllTasks(userId), HttpStatus.OK);
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<ApiResponse> updateTask(@PathVariable Integer id, @Valid @RequestBody Task task) {
//         return new ResponseEntity<>(service.updateTask(task, id), HttpStatus.OK);
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<String> deleteTaskById(@PathVariable Integer id) {
//         service.deleteTask(id);
//         return ResponseEntity.ok("Task deleted successfully");
//     }

//     @PatchMapping("/{id}/task-done")
//     public ResponseEntity<ApiResponse> completedTodo(@PathVariable Integer id) {
//         return  ResponseEntity.ok(service.doneTask(id));
//     }

//     @PatchMapping("/{id}/task-pending")
//     public ResponseEntity<ApiResponse> inCompletedTodo(@PathVariable Integer id){
//         return ResponseEntity.ok(service.pendingTask(id));
//     }
// }