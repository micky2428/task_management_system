package com.taskmanagement.auth.security;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    @NonNull private Long id;
    @NonNull private String username;
    @NonNull private String password;
    @NonNull private String email;
    @NonNull private Collection<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}

//讓 Spring Security 知道如何處理應用程式中的用戶資料，並將它與 Spring Security 的身份驗證機制結合。