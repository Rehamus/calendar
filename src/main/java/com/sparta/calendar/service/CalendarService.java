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

        String email ="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        Calendar calendar = new Calendar(requestDto);
        if(requestDto.getManager().matches(email)) {
            Calendar saveCalendar = calendarRepository.save(calendar);
            CalendarResponseDto calendarResponseDto = new CalendarResponseDto(saveCalendar);
            return calendarResponseDto;
        }else{
            throw new IllegalArgumentException("담당자가 이메일 형식이 아닙니다.");
        }
    }

    // 전채확인
    public List<CalendarResponseDto> getCalendar() {
        return calendarRepository.findAllByOrderByCreatedDesc().stream()
                .map(CalendarResponseDto::new).toList();
    }

    // 수정
    @Transactional
    public String updateCalendar(String todo, String password ,CalendarRequestDto requestDto){
        Calendar calendar = findCalendarTodo(todo);

        if(calendar.getPassword().equals(password)) {
            calendar.update(requestDto);
            return todo;
        }else{
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
    }

    //삭제
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
        return calendarRepository.findAll().stream()
                .filter( C -> C.getTodo().equals(todo) )
                .map(CalendarResponseDto::new).toList();
    }


    // 일정찾기
    private Calendar findCalendarTodo(String todo) {
        Calendar calendar = calendarRepository.findByTodo(todo);
        if (calendar == null) {
            throw new IllegalArgumentException( todo + "라는 일정은 없습니다.");
        }
        return calendar;

    }

}
