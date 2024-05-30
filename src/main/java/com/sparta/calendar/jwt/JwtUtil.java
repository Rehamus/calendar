package com.sparta.calendar.jwt;

import com.sparta.calendar.entitiy.User;
import com.sparta.calendar.entitiy.UserRoleEnum;
import com.sparta.calendar.repository.UserRepository;
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

import java.io.IOException;
import java.io.PrintWriter;
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
    public static final long Access_TOKEN_TIME = 60*60*1000L;
    public static final long REFRESH_TOKEN_TIME = 7*24*60*90*1000L;
    private final UserRepository userRepository;

    @Value("${jwt.secret.key}")
    private String jwtKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //키값 암호화
    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode( jwtKey);
        key = Keys.hmacShaKeyFor( keyBytes );

    }

    //토큰 제작
    public String createToken(String username, UserRoleEnum roleEnum, long TIME) {
        Date now = new Date();

        return TOKEN_PREFIX + Jwts.builder()
                .setSubject(username)
                .claim(TOKEN_KEY, roleEnum)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TIME ))
                .signWith(key,signatureAlgorithm)
                .compact();
    }

    //refresh 토큰 생성
    public String createAccessToken(String username, UserRoleEnum roleEnum) {
        return createToken(username, roleEnum, Access_TOKEN_TIME);
    }

    //refresh 토큰 생성
    public String createRefreshToken(String username, UserRoleEnum roleEnum) {
        return createToken(username, roleEnum, REFRESH_TOKEN_TIME);
    }




    // 토큰 저장
    public void addToken(String token, HttpServletResponse response) {
        token = URLEncoder.encode( token, StandardCharsets.UTF_8 ).replaceAll( "\\+", "%20");

        Cookie cookie = new Cookie( HEADER_STRING, token);
        cookie.setPath( "/" );

        response.addCookie( cookie );

    }

    // 토큰 검증
    public boolean validateToken(String token,HttpServletResponse response) {
        try {
            Jwts.parserBuilder().setSigningKey( key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Exception, 만료된 JWT Access token 입니다.");
            handleExpiredToken(e,token, response);
        } catch (UnsupportedJwtException e) {
            ErrorMessage( response, "Unsupported Jwt Exception, 지원되지 않는 Access JWT 토큰 입니다.");
        } catch (SignatureException | MalformedJwtException e) {
            ErrorMessage( response, "Invalid JWT signature, 유효하지 않는  Access JWT 서명 입니다.");
        } catch (IllegalArgumentException e) {
            ErrorMessage( response, "JWT claims is empty, 잘못된 Access JWT 토큰 입니다.");
        }
        return false;
    }

    //Refresh 전용 토큰 검사기
    public boolean validateRefreshToken(String token, HttpServletResponse response) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            ErrorMessage( response, "Expired JWT Exception, 만료된 JWT Refresh token 입니다.\n로그인을 해 주세요 : @Post : /api/user/login");
        } catch (UnsupportedJwtException e) {
            ErrorMessage( response, "Unsupported Jwt Exception, 지원되지 않는 JWT Refresh 토큰 입니다.");
        } catch (SignatureException | MalformedJwtException e) {
            ErrorMessage( response, "Invalid JWT signature, 유효하지 않는 JWT Refresh 서명 입니다.");
        } catch (IllegalArgumentException e) {
            ErrorMessage( response, "JWT claims is empty, 잘못된 JWT Refresh 토큰 입니다.");
        }
        return false;
    }

    private void ErrorMessage(HttpServletResponse response, String errorMessage) {
        response.setStatus(400);
        response.setContentType("text/plain;charset=UTF-8");

        try (PrintWriter writer = response.getWriter()) {
            writer.write(errorMessage);
        } catch (IOException ioException) {
            logger.error("응답 오류", ioException);
        }
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

    //  토큰 말료시  토큰 생성
    private void handleExpiredToken(ExpiredJwtException e,String token, HttpServletResponse response) {
        String username = e.getClaims().getSubject();
        User user = userRepository.findByUsername(username).orElseThrow(()
        -> new IllegalArgumentException("해당 유저가 존제하지 않습니다."));
        if(!validateRefreshToken(substringToken( user.getRefreshToken() ) ,response)){
/*            String Refresh_token = createRefreshToken( user.getUsername(), user.getRole() );
            user.setRefreshToken(Refresh_token);*/
            return;
        }
        if (user != null) {
            newAccessToken( response, user );
        } else {
            logger.error("Refresh token 을 찾을 수 없습니다.");
        }
    }

    //  Access토큰 말료시  Access토큰 생성
    private void newAccessToken(HttpServletResponse response, User user) {
        String newAccessToken = createAccessToken( user.getUsername() , user.getRole());
        addToken( newAccessToken, response );
        response.setStatus( 400);
        response.setContentType( "text/plain;charset=UTF-8");

        try (PrintWriter writer = response.getWriter()) {
            writer.write("Access 토큰이 재생성되었습니다 다시 시도해 주세요");
        } catch (IOException ex) {
            logger.error("응답 오류");
        }
    }

    // HttpServletRequest에서 유져 가져오기
    public User gettokenUser (HttpServletRequest request) {
        String No_substring_token = gettokenJwt( request );
        String token = substringToken( No_substring_token );
        Claims token_user = parseToken( token );

        return userRepository.findByUsername( token_user.getSubject() )
                .orElseThrow( () -> new NullPointerException( "회원을 찾을 수 없습니다." ) );
    }

}
