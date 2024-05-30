package com.sparta.calendar.service;

import com.sparta.calendar.dto.CalendarRequestDto;
import com.sparta.calendar.dto.CalendarResponseDto;
import com.sparta.calendar.entitiy.Calendar;
import com.sparta.calendar.entitiy.User;
import com.sparta.calendar.jwt.JwtUtil;
import com.sparta.calendar.repository.CalendarRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sparta.calendar.entitiy.UserRoleEnum.ADMIN;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final JwtUtil jwtUtil;

    // 생성
    @Transactional
    public CalendarResponseDto createCalendar(HttpServletRequest request , CalendarRequestDto requestDto) {
        User user = jwtUtil.gettokenUser( request );
        Calendar calendar = new Calendar(requestDto ,user);
        user.getCalendarlist().add(calendar);
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
    public String updateCalendar(HttpServletRequest request ,String todo,CalendarRequestDto requestDto){
        Calendarcheck(request, todo).update(requestDto);;
        return "수정 완료";
    }


    //삭제
    @Transactional
    public String deleteCalendar(HttpServletRequest request ,String todo){
        calendarRepository.delete(Calendarcheck(request,todo));
        return "삭제 완료";
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

    // 유저 검증 및 비밀번호 검증
    private Calendar Calendarcheck(HttpServletRequest request,String todo) {
        Calendar calendar = findCalendarTodo(todo);
        User user = jwtUtil.gettokenUser( request );
        if(user.getRole().equals( ADMIN )){
            return calendar;
        }
        if(!user.getUsername().equals( calendar.getUsername())){
            throw new IllegalArgumentException(todo+"의 일정 주인이 아닙니다.");
        }
        return calendar;
    }

}
