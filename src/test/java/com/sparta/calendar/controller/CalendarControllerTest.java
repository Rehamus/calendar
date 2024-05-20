/*
package com.sparta.calendar.controller;

import com.sparta.calendar.dto.CalendarRequestDto;
import com.sparta.calendar.dto.CalendarResponseDto;
import com.sparta.calendar.entitiy.Calendar;
import com.sparta.calendar.repository.CalendarRepository;
import com.sparta.calendar.service.CalendarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CalendarController.class)
class CalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CalendarRepository calendarRepository;

    @InjectMocks
    private CalendarService calendarService;



    @DisplayName( "일정 생성" )
    @Test
    void createCalendar() {

//        given
        Calendar calendar = new Calendar(1L, "일정명","제목","내용","ham@burger.com","0000");
        CalendarRequestDto calendarRequestDto = new CalendarRequestDto("일정명","제목","내용","ham@burger.com","0000");
        CalendarResponseDto calendarResponseDto = new CalendarResponseDto(calendar);

        given(calendarRepository.save(any(Calendar.class))).willReturn(calendar);

//        when
        CalendarResponseDto test_responseDto = calendarService.createCalendar(calendarRequestDto);

//        then
        Assertions.assertEquals(calendarResponseDto.getId(), test_responseDto.getId());
        Assertions.assertEquals(calendarResponseDto.getTodo(), test_responseDto.getTodo());
        Assertions.assertEquals(calendarResponseDto.getTitle(), test_responseDto.getTitle());
        Assertions.assertEquals(calendarResponseDto.getContents(), test_responseDto.getContents());
        Assertions.assertEquals(calendarResponseDto.getManager(), test_responseDto.getManager());
    }

    @DisplayName( "일정 전채 확인" )
    @Test
    void getCalendar() {
        //        given
        Calendar calendar = new Calendar(1L, "일정명","제목","내용","ham@burger.com","0000");
        CalendarResponseDto calendarResponseDto = new CalendarResponseDto(calendar);

        given(calendarRepository.findByTodo(any(String.class))).willReturn(calendar);

//        when
        CalendarResponseDto test_responseDto = (CalendarResponseDto) calendarService.getTodo( "일정명");

//        then
        Assertions.assertEquals(calendarResponseDto, test_responseDto);
    }

    @DisplayName( "특정 일정 확인" )
    @Test
    void getTodo() {
    }

    @DisplayName( "일정 수정" )
    @Test
    void updateCalendar() {
    }

    @DisplayName( "일정 제거" )
    @Test
    void deleteCalendar() {
    }
}*/
