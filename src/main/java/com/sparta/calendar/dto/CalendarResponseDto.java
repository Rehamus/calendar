package com.sparta.calendar.dto;

import com.sparta.calendar.entitiy.Calendar;
import com.sparta.calendar.entitiy.DayStamp;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CalendarResponseDto {
    private Long id;
    private String todo;
    private String title;
    private String contents;
    private String manager;
    private String password;
    private LocalDate created;


    public CalendarResponseDto(Calendar saveCalendar) {
        this.id = saveCalendar.getId();
        this.todo = saveCalendar.getTodo();
        this.title = saveCalendar.getTitle();
        this.contents = saveCalendar.getContents();
        this.manager = saveCalendar.getManager();
        this.password = saveCalendar.getPassword();
        this.created = saveCalendar.getCreated();
    }
}
