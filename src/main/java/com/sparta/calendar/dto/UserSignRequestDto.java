package com.sparta.calendar.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserSignRequestDto {

    private String nickname;

    @Pattern(regexp =  "^[a-z0-9]{4,10}$",
            message = "사용자 이름은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9) 입니다")
    private String username;

    @Pattern(regexp =  "^[a-zA-Z0-9]{8,15}$",
            message = "사용자 암호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9) 입니다.")
    private String password;

    private boolean admin = false;

    private String admincheck = "";


}
