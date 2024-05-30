package com.sparta.calendar.dto;

import com.sparta.calendar.entitiy.Calendar;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class CalendarResponseDto {
    private Long id;
    private String todo;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime created;


    public CalendarResponseDto(Calendar saveCalendar) {
        this.id = saveCalendar.getId();
        this.todo = saveCalendar.getTodo();
        this.username = saveCalendar.getUsername();
        this.title = saveCalendar.getTitle();
        this.contents = saveCalendar.getContents();
        this.created = saveCalendar.getCreated();
    }

    public CalendarResponseDto() {

    }
}
