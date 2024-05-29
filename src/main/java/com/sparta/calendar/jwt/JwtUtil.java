package com.sparta.calendar.jwt;

import com.sparta.calendar.entitiy.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_KEY = "auth";
    public static final long EXPIRATION_TIME = 60*60*1000L;

    @Value("${jwt.secret.key}")
    private String jwtKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    //키값 암호화
    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode( jwtKey);
        key = Keys.hmacShaKeyFor( keyBytes );

    }

    //토큰 제작
    public String createToken(String username, UserRoleEnum roleEnum) {
        Date now = new Date();

        return TOKEN_PREFIX + Jwts.builder()
                .setSubject(username)
                .claim(TOKEN_KEY, roleEnum)
                .setExpiration(new Date(now.getTime() + EXPIRATION_TIME))
                .setIssuedAt(now)
                .signWith(key,signatureAlgorithm)
                .compact();
    }

    // 토큰 저장
    public void addToken(String token, HttpServletResponse response) {
        token = URLEncoder.encode( token, StandardCharsets.UTF_8 ).replaceAll( "\\+", "%20");

        Cookie cookie = new Cookie( HEADER_STRING, token);
        cookie.setPath( "/" );

        response.addCookie( cookie );

    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey( key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Exception, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported Jwt Exception, 지원되지 않는 JWT 토큰 입니다.");
        } catch (SignatureException | MalformedJwtException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }


    //토큰 값만 가져오기
    public String substringToken(String token) {
        if(StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }
        logger.error( "토큰이 없음" );
        throw new NullPointerException("토큰이 없음");
    }

    //토큰에서 user 가져오기
    public Claims parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey( key).build().parseClaimsJws(token).getBody();
    }

    // HttpServletRequest에서 JWT뽑아오기
    public String gettokenJwt(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals(HEADER_STRING)) {
                    return URLDecoder.decode( cookie.getValue(), StandardCharsets.UTF_8 );
                }
            }
        }
        return null;
    }
}
