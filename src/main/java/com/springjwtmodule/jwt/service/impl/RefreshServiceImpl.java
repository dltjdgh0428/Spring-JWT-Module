package com.springjwtmodule.jwt.service.impl;

import com.springjwtmodule.dto.oauth.RefreshDto;
import com.springjwtmodule.jwt.domain.Refresh;
import com.springjwtmodule.jwt.repository.RefreshRepository;
import com.springjwtmodule.jwt.service.RefreshService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshServiceImpl implements RefreshService {

    private final RefreshRepository refreshRepository;

    public void 리프레시토큰삭제(String refresh) {
        refreshRepository.deleteByRefresh(refresh);
    }

    public boolean 리프레시토큰조회(String refresh) {
        return refreshRepository.existsByRefresh(refresh);
    }

    public void 리프레시토큰생성(RefreshDto refreshDto) {
        Date date = new Date(System.currentTimeMillis() + refreshDto.getExpiredMs());

        Refresh refresh = refreshDto.toEntity(date);

        refreshRepository.save(refresh);

    }
}
