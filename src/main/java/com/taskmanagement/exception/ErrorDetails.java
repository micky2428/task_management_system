package com.taskmanagement.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor //有這個就不用this...那段
@NoArgsConstructor  
public class ErrorDetails {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    // 使用 @AllArgsConstructor 和 @NoArgsConstructor 生成合适的构造函数
    public static ErrorDetails of(String message, HttpStatus status) {
        return new ErrorDetails(status.value(), message, LocalDateTime.now());
    }
}

//錯誤訊息的詳細資料