package com.sparta.calendar.service;

import com.sparta.calendar.dto.CalendarRequestDto;
import com.sparta.calendar.dto.CalendarResponseDto;
import com.sparta.calendar.entitiy.Calendar;
import com.sparta.calendar.repository.CalendarRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;

    public CalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    // 생성
    public CalendarResponseDto createCalendar(CalendarRequestDto requestDto) {

        Calendar calendar = new Calendar( requestDto);
        Calendar saveCalendar = calendarRepository.save(calendar);
        CalendarResponseDto calendarResponseDto = new CalendarResponseDto(saveCalendar);
        return calendarResponseDto;
    }

    // 전채확인
    public List<CalendarResponseDto> getCalendar() {
        return calendarRepository.findAllByOrderByCreatedDesc().stream()
                .map(CalendarResponseDto::new).toList();
    }

    // 수정
    @Transactional
    public String updateCalendar(String todo, String password ,CalendarRequestDto requestDto) {
        Calendar calendar = calendarRepository.findByTodo(todo);

        if(calendar.getPassword().equals(password)) {
            calendar.update(requestDto);
        }else{
            System.out.println("비번아님");
        }
        return todo;
    }

    //삭제
    public String deleteCalendar(String todo ,String password) {
        Calendar calendar = calendarRepository.findByTodo(todo);

        if(calendar.getPassword().equals(password)) {
            calendarRepository.delete(calendar);
        }else{
            System.out.println("비번아님");
        }
        return todo;
    }

    // 일정 확인
    public List<CalendarResponseDto> getTodo(String todo) {
        return calendarRepository.findAll().stream()
                .filter( C -> C.getTodo().equals(todo) )
                .map(CalendarResponseDto::new).toList();
    }


}
