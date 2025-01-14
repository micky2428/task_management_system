package com.taskmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public final class ApiResponseDTO<T> {
    
    private final String message;
    //使用泛型 T 來表示 data 的類型
    private final T data;
}

