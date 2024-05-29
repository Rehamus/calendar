package com.sparta.calendar.controller;

import com.sparta.calendar.dto.UserLoginRequestDto;
import com.sparta.calendar.dto.UserRequestDto;
import com.sparta.calendar.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/sign")
    public String sign(@RequestBody @Valid UserRequestDto userRequestDto) {
        userService.sign(userRequestDto);
        return "가입 완료";
    }

    @PostMapping("/user/login")
    public String sign(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        userService.login( userLoginRequestDto, response);
        return "로그인 완료";
    }



}
