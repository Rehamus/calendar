package com.sparta.calendar.controller;

import com.sparta.calendar.dto.CalendarRequestDto;
import com.sparta.calendar.dto.CalendarResponseDto;
import com.sparta.calendar.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CalendarController {
    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    // 일정만
    @PostMapping("days")
    @Operation(summary = "일정 생성")
    @Parameters({
            @Parameter(name = "todo",description = "일정"),
            @Parameter(name = "title",description = "일정 제목"),
            @Parameter(name = "contents",description = "일정 내용"),
    })
    public CalendarResponseDto createCalendar(HttpServletRequest request, @RequestBody @Valid CalendarRequestDto requestDto) {
        return calendarService.createCalendar(request,requestDto);
    }

    //사진 + 일정
    @PostMapping("days/p")
    @Operation(summary = "일정 생성")
    @Parameters({
        @Parameter(name = "picture",description = "사진"),
        @Parameter(name = "calendarRequestDto",
                description = "{ \"todo\": \"Sample todo6\", \"title\": \"Sample title\", \"contents\": \"Sample contents\" }"),
    })
    public CalendarResponseDto createCalendar(
            HttpServletRequest request,
            @RequestPart("picture") MultipartFile picture,
            @RequestPart("calendarRequestDto") String calendarRequestDto) {
        return calendarService.createCalendar(request,picture,calendarRequestDto);
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
            @Parameter(name = "contents",description = "일정 내용")
    })
    public String updateCalendar(HttpServletRequest request ,@PathVariable String todo, @RequestBody CalendarRequestDto requestDto) {
        return calendarService.updateCalendar(request,todo,requestDto);
    }

    @Operation(summary = "일정 삭제")
    @DeleteMapping("days/{todo}")
    public String deleteCalendar(HttpServletRequest request ,@PathVariable String todo){
        return calendarService.deleteCalendar(request,todo);
    }

}
