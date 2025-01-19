package com.taskmanagement.auth.security;
import java.util.List;

//用List，因使用者可能有多個角色
public record AuthResponse(Long id, String username, List<String> roles) {
}

//封裝身份驗證成功後的回應資料，通常包括用戶的 ID、用戶名和角色等。


