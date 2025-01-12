package com.taskmanagement.tasks.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.taskmanagement.dto.Login;
import com.taskmanagement.dto.Register;
import com.taskmanagement.model.User;
import com.taskmanagement.security.AuthResponse;
import com.taskmanagement.service.AuthService;


@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //將請求的 JSON 資料轉換為 Register 物件，並進行驗證。@Valid 確保 Register 物件中的字段符合驗證約束（例如必填、長度限制等）。
    //調用 AuthService 中的註冊方法，並將 register 物件傳遞給它。這個方法處理用戶註冊的邏輯，並返回 AuthResponse 物件
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody Register register){
        AuthResponse authResponse = authService.register(register);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody Login login){
        
        //map 是 Optional 類中的一個函數式方法，作用是對 Optional 中的值進行操作，並返回一個新的 Optional。
        return authService.login(login)
            //如果 login 成功，則將用戶資訊映射為 AuthResponse 物件，包含用戶的 id、username 和 role
            .map(user -> new AuthResponse(user.getId(), user.getUsername(), user.getRole()))
            .map(authResponse -> ResponseEntity.ok(authResponse)) // 若登入成功返回 200
            .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()); // 若登入失敗返回 401
    }
}