package com.sparta.calendar.service;

import com.sparta.calendar.dto.CalendarRequestDto;
import com.sparta.calendar.dto.CalendarResponseDto;
import com.sparta.calendar.entitiy.Calendar;
import com.sparta.calendar.repository.CalendarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;


    // 생성
    @Transactional
    public CalendarResponseDto createCalendar(CalendarRequestDto requestDto) {
        Calendar calendar = new Calendar(requestDto);
        Calendar saveCalendar = calendarRepository.save(calendar);
        return new CalendarResponseDto( saveCalendar);
    }

    // 전채확인
    public List<CalendarResponseDto> getCalendar() {
        return calendarRepository.findAllByOrderByCreatedDesc().stream()
                .map(CalendarResponseDto::new).toList();
    }

    // 수정
    @Transactional
    public String updateCalendar(String todo,CalendarRequestDto requestDto){
        Calendar calendar = findCalendarTodo(todo);

        if(requestDto.getPassword().equals(calendar.getPassword())) {
            calendar.update(requestDto);
            return todo;
        }else{
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
    }

    //삭제
    @Transactional
    public String deleteCalendar(String todo ,String password){
        Calendar calendar = findCalendarTodo(todo);

        if(calendar.getPassword().equals(password)) {
            calendarRepository.delete(calendar);
            return todo;
        }else{
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
    }

    // 일정 확인
    public List<CalendarResponseDto> getTodo(String todo) {
        findCalendarTodo(todo);
        return calendarRepository.findAll().stream()
                .filter( C -> C.getTodo().equals(todo) )
                .map(CalendarResponseDto::new).toList();
    }


    // 일정찾기
    public Calendar findCalendarTodo(String todo) {
        Calendar calendar = calendarRepository.findByTodo(todo);
        if (calendar == null) {
            throw new IllegalArgumentException( todo + "라는 일정은 없습니다.");
        }
        return calendar;

    }

}
