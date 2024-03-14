package com.springjwtmodule.controller;

import com.springjwtmodule.dto.CMRespDto;
import com.springjwtmodule.dto.oauth.RefreshDto;
import com.springjwtmodule.jwt.JWTUtil;
import com.springjwtmodule.service.RefreshService;
import com.springjwtmodule.service.impl.RefreshServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final JWTUtil jwtUtil;
    private final RefreshService refreshService;

    @PostMapping("/reissue")
    public CMRespDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            //response status code
            return new CMRespDto<>(HttpStatus.BAD_REQUEST, null, "refresh token null");
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            //response status code
            return new CMRespDto<>(HttpStatus.BAD_REQUEST, null, "refresh token expired");
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            return new CMRespDto<>(HttpStatus.BAD_REQUEST, null, "invalid refresh token");
        }

        boolean isExist = refreshService.리프레시토큰조회(refresh);
        if (!isExist) {
            return new CMRespDto<>(HttpStatus.BAD_REQUEST, null, "invalid refresh token");
        }


        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        refreshService.리프레시토큰삭제(refresh);
        refreshService.리프레시토큰생성(new RefreshDto(username, newRefresh, 86400000L));

        //response
        response.setHeader("access", newAccess);
        response.addCookie(jwtUtil.createCookie("refresh", newRefresh));

        return new CMRespDto<>(HttpStatus.OK, null, "재발급 완료");
    }


}