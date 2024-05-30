package com.sparta.calendar.controller;

import com.sparta.calendar.dto.UserLoginRequestDto;
import com.sparta.calendar.dto.UserSignRequestDto;
import com.sparta.calendar.jwt.JwtUtil;
import com.sparta.calendar.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/user/sign")
    @Operation(summary = "회원가입")
    @Parameters({
            @Parameter(name = "nickname",description = "별명"),
            @Parameter(name = "username",description = "아이디"),
            @Parameter(name = "password",description = "비밀번호"),
            @Parameter(name = "admin",description = "권한"),
            @Parameter(name = "admincheck",description = "권한"),
    })
    public String sign(@RequestBody @Valid UserSignRequestDto usersignRequestDto,HttpServletResponse response) {
        return userService.sign( usersignRequestDto,response );
    }

    @PostMapping("/user/login")
    @Operation(summary = "로그인")
    @Parameters({
            @Parameter(name = "username",description = "아이디"),
            @Parameter(name = "password",description = "비밀번호")
    })
    public String login(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        return userService.login(userLoginRequestDto, response);
    }

    @DeleteMapping("/user/delete")
    @Operation(summary = "회원 탈퇴")
    public String delete(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletRequest request) {
        return userService.delete( userLoginRequestDto, request);
    }


}
