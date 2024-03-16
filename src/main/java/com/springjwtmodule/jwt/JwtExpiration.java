package com.springjwtmodule.jwt;

public enum JwtExpiration {
    ACCESS_TOKEN_EXPIRATION(600000L), // 액세스 토큰 만료 시간
    REFRESH_TOKEN_EXPIRATION(86400000L); // 리프레시 토큰 만료 시간

    private final Long expirationTime;

    JwtExpiration(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }
}
