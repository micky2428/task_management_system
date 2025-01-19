package com.taskmanagement.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//自動生成一個無參構造方法，適用於需要無參初始化的類。適用於Spring、JPA 需要一個無參構造函數來創建實例
@NoArgsConstructor(force = true)  // 強制 Lombok 初始化 final 字段
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    // 构造函数，接受 HttpStatus 和 String 参数
    public ApiException(HttpStatus status, String message) {
        super(message);  // 调用父类 RuntimeException 的构造函数来设置错误消息
        this.status = status;  // 设置 status
        this.message = message; // 设置 message
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
//API 異常，通常會與 HTTP 狀態碼一同返回