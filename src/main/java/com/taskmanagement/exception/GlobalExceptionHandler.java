package com.taskmanagement.exception;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

//捕獲全局異常
@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorDetails> buildResponse(Object exceptionOrMessage, HttpStatus status, WebRequest request) {
        String message;
        if (exceptionOrMessage instanceof Exception) {
            // 如果是 Exception 类型，提取其消息
            message = ((Exception) exceptionOrMessage).getMessage();
        } else {
            // 如果是 String 类型，直接使用它作为错误消息
            message = exceptionOrMessage.toString();
        }
    
        // 创建 ErrorDetails 实例
        ErrorDetails errorDetails = new ErrorDetails(status.value(), message, LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, status);
    }

    //處理資源未找到的情況(404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest request) {
        return buildResponse(exception, HttpStatus.NOT_FOUND, request);
    }
    //處理業務層面的 API 異常
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDetails> handleApiException(ApiException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), ex.getStatus(), request);
    }

    //非法參數
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.BAD_REQUEST, request);
    }
    //權限不足(403)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.FORBIDDEN, request);
    }

    //處理參數校驗異常 (400) -> 取得所有錯誤訊息
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        String message = "請求參數驗證失敗：" + String.join("; ", errors);
        return buildResponse(message, HttpStatus.BAD_REQUEST, request);
    }

    //Exception 作為Fallback Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception ex, WebRequest request) {
        return buildResponse("系統發生錯誤，請稍後再試", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


}

//處理所有捕獲到的異常