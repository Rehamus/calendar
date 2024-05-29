package com.sparta.calendar.filter;

import com.sparta.calendar.entitiy.User;
import com.sparta.calendar.jwt.JwtUtil;
import com.sparta.calendar.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class UserAuthFilter implements Filter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();

        if(StringUtils.hasText(uri) && uri.startsWith("/api/user")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String No_substring_token = jwtUtil.gettokenJwt( request);
            if(StringUtils.hasText( No_substring_token )) {
                String token = jwtUtil.substringToken( No_substring_token );
                if (!jwtUtil.validateToken( token )) {
                    sendErrorResponse( response,400,"토큰이 유효하지 않습니다.");
                }
                Claims token_user = jwtUtil.parseToken( token );

                User user = userRepository.findByUsername( token_user.getSubject() )
                        .orElseThrow(() -> new NullPointerException("회원을 찾을 수 없습니다.") );

                servletRequest.setAttribute( "username",user );
                filterChain.doFilter(servletRequest, servletResponse);
            }else {
                sendErrorResponse( response,400,"토큰이 유효하지 않습니다.");return;
            }
        }
    }
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(message);
    }
}
