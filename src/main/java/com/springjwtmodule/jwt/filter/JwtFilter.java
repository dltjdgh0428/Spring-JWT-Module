package com.springjwtmodule.jwt.filter;

import com.springjwtmodule.config.oauth.CustomUserDetails;
import com.springjwtmodule.entity.user.User;
import com.springjwtmodule.jwt.token.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

import static com.springjwtmodule.jwt.token.TokenType.ACCESS;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(ACCESS.getType());

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!validateToken(response, accessToken)) {
            return;
        }

        setupAuthentication(accessToken);

        filterChain.doFilter(request, response);
    }

    private boolean validateToken(HttpServletResponse response, String accessToken) throws IOException {
        try {
            if (jwtProvider.isExpired(accessToken) || !ACCESS.getType().equals(jwtProvider.getCategory(accessToken))) {
                sendErrorResponse(response, "Invalid or expired access token", HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        } catch (Exception e) { // 넓은 범위의 예외 처리를 통해 다양한 에러 상황을 처리할 수 있습니다.
            sendErrorResponse(response, "Token validation error", HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    private void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        try (PrintWriter writer = response.getWriter()) {
            writer.print(message);
        }
    }

    private void setupAuthentication(String accessToken) {
        String username = jwtProvider.getUsername(accessToken);
        String role = jwtProvider.getRole(accessToken);

        User userEntity = User.builder()
                .username(username)
                .role(role)
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}

