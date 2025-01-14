package com.taskmanagement.dto;
//DTO 是跨功能模組使用的類型

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
//支持靈活地創建物件，特別是當需要對部分欄位進行初始化時，代碼更加清晰(而非@setter)
@Builder
////final:不可變的 DTO 提高了安全性
public final class RegisterRequest {

    @NotEmpty(message = "Invalid: username cannot be empty")
    private String username;

    @NotEmpty(message = "Invalid: email cannot be empty")
    @Email(message = "Invalid: email format is incorrect")
    private String email;

    @NotEmpty(message = "Invalid: password cannot be empty")
    @Size(min = 8, message = "Invalid: password must be at least 8 characters long")
    private String password;
}