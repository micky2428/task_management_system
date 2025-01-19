package com.taskmanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//final:不可變的 DTO 提高了安全性
public final class LoginRequest {

    @NotEmpty(message = "Username cannot be empty. Please provide a valid username.")
    private String username;

    @NotEmpty(message = "Password cannot be empty. Please provide a valid password.")
    private String password;
}