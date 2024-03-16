package com.springjwtmodule.dto.oauth;

import com.springjwtmodule.jwt.domain.Refresh;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class RefreshDto {
    private String username;
    private String refresh;
    private Long expiredMs;

    public Refresh toEntity(Date date) {
        return Refresh.builder()
                .username(username)
                .refresh(refresh)
                .expiration(date.toString())
                .build();
    }
}
