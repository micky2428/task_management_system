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
public class UserController {
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


// package com.taskmanagement.auth.controller;

// import jakarta.validation.Valid;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import taskmanagementsystem.dto.Login;
// import taskmanagementsystem.dto.Register;
// import taskmanagementsystem.model.User;
// import taskmanagementsystem.security.AuthResponse;
// import taskmanagementsystem.service.AuthService;

// import java.util.Optional;

// @RestController
// @RequestMapping("api/auth")
// public class AuthController {
//     private final AuthService authService;

//     public AuthController(AuthService authService) {
//         this.authService = authService;
//     }

//     @ResponseStatus(HttpStatus.CREATED)
//     @PostMapping("/register")
//     public ResponseEntity<AuthResponse> register(@Valid @RequestBody Register register){
//         return ResponseEntity.ok(authService.register(register));
//     }

//     @PostMapping("/login")
//     public ResponseEntity<AuthResponse> login(@Valid @RequestBody Login login){
//         Optional<User> user = authService.login(login);
//         if (user.isPresent()){
//             User presentUser = user.get();
//             return ResponseEntity.ok(new AuthResponse(presentUser.getId(), presentUser.getUsername(), presentUser.getRole()));
//         }
//         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//     }
// }