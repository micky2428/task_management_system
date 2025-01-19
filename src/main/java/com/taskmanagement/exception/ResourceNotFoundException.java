package com.taskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//自定義異常類，用於表示資源未找到的情況
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        //當子類覆蓋了父類的方法，但仍然希望在新的方法中調用父類的原始方法
        super(message);
    }

    public ResourceNotFoundException() {
        super("Resource not found");
    }

    @Override
    public String toString() {
        return "ResourceNotFoundException{" +
                "message='" + getMessage() + '\'' +
                '}';
    }
}