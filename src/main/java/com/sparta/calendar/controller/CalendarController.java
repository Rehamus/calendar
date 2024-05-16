package com.sparta.calendar.controller;

import com.sparta.calendar.dto.CalendarRequestDto;
import com.sparta.calendar.dto.CalendarResponseDto;
import com.sparta.calendar.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Controller
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
    @Operation(summary = "특정 일정 생성")
    public List<CalendarResponseDto> getTodo(@PathVariable String todo) {
        return calendarService.getTodo(todo);
    }

    @PutMapping("days/{todo}/{password}")
    @Operation(summary = "일정 수정")
    @Parameters({
            @Parameter(name = "title",description = "일정 제목"),
            @Parameter(name = "contents",description = "일정 내용"),
            @Parameter(name = "manager",description = "담당자"),
    })
    public String updateCalendar(@PathVariable String todo, @PathVariable String password, @RequestBody CalendarRequestDto requestDto) throws IllegalAccessException {
        return calendarService.updateCalendar(todo, password ,requestDto);
    }

    @Operation(summary = "일정 삭제")
    @DeleteMapping("days/{todo}/{password}")
    public String deleteCalendar(@PathVariable String todo, @PathVariable String password) throws IllegalAccessException {
        return calendarService.deleteCalendar(todo,password);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>( Objects.requireNonNull( e.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}
