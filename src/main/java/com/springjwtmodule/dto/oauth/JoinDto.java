package com.springjwtmodule.dto.oauth;

import com.springjwtmodule.entity.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinDto {

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    private String username;

    @NotEmpty
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{3,20}$") // 영문, 특수문자 8자 이상 20자 이하
    private String password;

    public User toEntity(String password,String role) {
        return User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}
