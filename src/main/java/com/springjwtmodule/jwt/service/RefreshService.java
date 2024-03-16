package com.springjwtmodule.jwt.service;

import com.springjwtmodule.dto.oauth.RefreshDto;

public interface RefreshService {
    void 리프레시토큰삭제(String refresh);
    boolean 리프레시토큰조회(String refresh);
    void 리프레시토큰생성(RefreshDto refreshDto);
}
