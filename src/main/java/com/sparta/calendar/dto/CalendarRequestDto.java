package com.sparta.calendar.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;


@Getter
public class CalendarRequestDto {

    @Size(min = 1, max = 200)
    private String todo;

    @NonNull
    @Size(min = 1, max = 20)
    private String title;

    @NonNull
    @Size(min = 1, max = 500)
    private String contents;
}
