package com.sparta.calendar.controller;

import com.sparta.calendar.dto.CalendarRequestDto;
import com.sparta.calendar.dto.CalendarResponseDto;
import com.sparta.calendar.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class CalendarController {
    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }


    @PostMapping("days")
    @Operation(summary = "일정 생성")
    @Parameters({
        @Parameter(name = "todo",description = "일정"),
        @Parameter(name = "title",description = "일정 제목"),
        @Parameter(name = "contents",description = "일정 내용"),
        @Parameter(name = "manager",description = "담당자"),
        @Parameter(name = "password",description = "비밀번호")
    })
    public CalendarResponseDto createCalendar(@Valid @RequestBody CalendarRequestDto requestDto) {
        return calendarService.createCalendar(requestDto);
    }


    @GetMapping("days")
    @Operation(summary = "일정 전체 확인")
    public List<CalendarResponseDto> getCalendar() {
        return calendarService.getCalendar();
    }

    @GetMapping("days/{todo}")
    @Operation(summary = "특정 일정 조회")
    public List<CalendarResponseDto> getTodo(@PathVariable String todo) {
        return calendarService.getTodo(todo);
    }

    @PutMapping("days/{todo}")
    @Operation(summary = "일정 수정")
    @Parameters({
            @Parameter(name = "title",description = "일정 제목"),
            @Parameter(name = "contents",description = "일정 내용"),
            @Parameter(name = "manager",description = "담당자"),
            @Parameter(name = "password",description = "비밀번호"),
    })
    public String updateCalendar(@PathVariable String todo, @RequestBody CalendarRequestDto requestDto) throws IllegalAccessException {
        return calendarService.updateCalendar(todo,requestDto);
    }

    @Operation(summary = "일정 삭제")
    @DeleteMapping("days/{todo}")
    @Parameters({
            @Parameter(name = "password",description = "비밀번호"),
    })
    public String deleteCalendar(@PathVariable String todo, @RequestBody Map<String,String> password) throws IllegalAccessException {
        return calendarService.deleteCalendar(todo,password.get( "password" ));
    }

}
