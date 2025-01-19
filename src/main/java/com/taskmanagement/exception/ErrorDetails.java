package com.taskmanagement.exception;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorDetails {
    //異常發生的時間戳
    private final LocalDateTime timeStamp;
    private final String message;
    private final String details;

    public static ErrorDetails of(String message, String details) {
        return new ErrorDetails(LocalDateTime.now(), message, details);
    }
}

//錯誤訊息的詳細資料