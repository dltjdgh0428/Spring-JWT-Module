package com.springjwtmodule.jwt;

public enum TokenType {
    ACCESS("access", 600000L),
    REFRESH("refresh", 86400000L);

    private final String type;
    private final Long expirationTime;

    TokenType(String type, Long expirationTime) {
        this.type = type;
        this.expirationTime = expirationTime;
    }

    public String getType() {
        return type;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

}
