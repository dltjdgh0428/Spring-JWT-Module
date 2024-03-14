package com.springjwtmodule;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import com.springjwtmodule.dto.oauth.RefreshDto;
import com.springjwtmodule.entity.refresh.RefreshRepository;
import com.springjwtmodule.service.RefreshService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class RefreshServiceImplTest {

    @Autowired
    private RefreshService refreshService;

    @MockBean
    private RefreshRepository refreshRepository;

    @Test
    @DisplayName("리프레시 토큰 생성 테스트")
    void 리프레시토큰생성_테스트() {
        RefreshDto refreshDto = new RefreshDto("ID", "token",1000L); // 1000L은 토큰 만료 시간
        given(refreshRepository.save(any())).willReturn(any());

        // When
        refreshService.리프레시토큰생성(refreshDto);

        // Then
        verify(refreshRepository).save(any());
    }

    @Test
    @DisplayName("리프레시 토큰 조회 테스트")
    void 리프레시토큰조회_테스트() {
        // Given
        String refreshToken = "token";
        given(refreshRepository.existsByRefresh(refreshToken)).willReturn(true);

        // When
        boolean exists = refreshService.리프레시토큰조회(refreshToken);

        // Then
        assertTrue(exists);
    }

    @Test
    @DisplayName("리프레시 토큰 삭제 테스트")
    void 리프레시토큰삭제_테스트() {
        // Given
        String refreshToken = "token";

        // When
        refreshService.리프레시토큰삭제(refreshToken);

        // Then
        verify(refreshRepository).deleteByRefresh(refreshToken);
        // 삭제 메소드가 해당 토큰으로 호출되었는지 확인
    }
}
