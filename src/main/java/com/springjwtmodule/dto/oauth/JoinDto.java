package com.springjwtmodule.dto.oauth;

import com.springjwtmodule.entity.user.User;
import lombok.Data;

@Data
public class JoinDto {
    private String username;
    private String password;

    public User toEntity(String password,String role) {
        return User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}
