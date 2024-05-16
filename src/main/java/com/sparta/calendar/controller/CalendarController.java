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

    @GetMapping("days/{todo}")
    public List<CalendarResponseDto> getTodo(@PathVariable String todo) {
        return calendarService.getTodo(todo);
    }

    @PutMapping("days/{todo}/{password}")
    public String updateCalendar(@PathVariable String todo, @PathVariable String password, @RequestBody CalendarRequestDto requestDto) {
        return calendarService.updateCalendar(todo, password ,requestDto);
    }

    @DeleteMapping("days/{todo}/{password}")
    public String deleteCalendar(@PathVariable String todo, @PathVariable String password) {
        return calendarService.deleteCalendar(todo,password);
    }


}
