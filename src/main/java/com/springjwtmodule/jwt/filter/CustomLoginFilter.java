package com.springjwtmodule.jwt.filter;

import com.springjwtmodule.dto.oauth.RefreshDto;
import com.springjwtmodule.jwt.token.JwtProvider;
import com.springjwtmodule.jwt.service.RefreshService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

import static com.springjwtmodule.jwt.token.TokenType.ACCESS;
import static com.springjwtmodule.jwt.token.TokenType.REFRESH;

@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshService refreshService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(obtainUsername(request), obtainPassword(request), null));
    }

    //JWT를 발급할 곳
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        String access = jwtProvider.createJwt(ACCESS.getType(), username, role, ACCESS.getExpirationTime());
        String refresh = jwtProvider.createJwt(REFRESH.getType(), username, role, REFRESH.getExpirationTime());

        refreshService.리프레시토큰생성(new RefreshDto(username, refresh, REFRESH.getExpirationTime()));

        //응답 설정
        response.setHeader(ACCESS.getType(), access);
        response.addCookie(jwtProvider.createCookie(REFRESH.getType(), refresh));
        response.setStatus(HttpStatus.OK.value());

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }


}
