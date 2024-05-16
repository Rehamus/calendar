package com.sparta.calendar.controller;

import com.sparta.calendar.dto.CalendarRequestDto;
import com.sparta.calendar.dto.CalendarResponseDto;
import com.sparta.calendar.service.CalendarService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("/Ctr/")
public class CalendarController {
    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @PostMapping("days")
    public CalendarResponseDto createCalendar(@RequestBody CalendarRequestDto requestDto) {
        return calendarService.createCalendar(requestDto);
    }

    @GetMapping("days")
    public List<CalendarResponseDto> getCalendar() {
        return calendarService.getCalendar();
    }

    @PutMapping("days/{id}")
    public Long updateCalendar(@PathVariable Long id, @RequestBody CalendarRequestDto requestDto) {
        return calendarService.updateCalendar(id, requestDto);
    }

    @DeleteMapping("days/{id}")
    public Long deleteCalendar(@PathVariable Long id) {
        return calendarService.deleteCalendar(id);
    }
}
