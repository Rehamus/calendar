package com.sparta.calendar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;


@Getter
@AllArgsConstructor
public class CalendarRequestDto {
    @NotBlank
    @Size(min = 1, max = 200)
    private String todo;
    @NonNull
    @Size(min = 1, max = 20)
    private String title;
    @NonNull
    @Size(min = 1, max = 500)
    private String contents;

    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "이메일 형식에 맞지 않습니다.")
    private String manager;

    @NonNull
    @Size(min = 1, max = 20)
    private String password;
}
