package com.taskmanagement.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.taskmanagement.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
//@RequiredArgsConstructor 自動幫我們注入 UserRepository，避免手動 private UserRepository userRepository;
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    //用 final 修飾，避免 userRepository 在 runtime 被修改
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        var user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username or email"));

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));

        return new User(
                user.getUsername(), // 修正為正確的 username
                user.getPassword(),
                authorities
        );
    }
}

//實作 UserDetailsService，用來從資料庫中加載用戶資料。