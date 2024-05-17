package com.sparta.calendar.controller;

import com.sparta.calendar.dto.CalendarRequestDto;
import com.sparta.calendar.dto.CalendarResponseDto;
import com.sparta.calendar.service.CalendarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CalendarControllerTest {

    @Mock
    private CalendarService calendarService;

    @InjectMocks
    private CalendarController calendarController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(calendarController)
                .setControllerAdvice(new ExceptionControllerAdvice())
                .build();
    }

    @Test
    public void testCreateCalendar() throws Exception {

        CalendarResponseDto responseDto = new CalendarResponseDto();
        given(calendarService.createCalendar(any(CalendarRequestDto.class))).willReturn(responseDto);


        mockMvc.perform(post("/api/days")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"todo\":\"치킨\",\"title\":\"허니콤보\",\"contents\":\"35000원\",\"manager\":\"korea@chicken.com\",\"password\":\"1234\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        then(calendarService).should(times(1)).createCalendar(any(CalendarRequestDto.class));
    }

    @Test
    public void testGetCalendar() throws Exception {

        List<CalendarResponseDto> responseList = Collections.singletonList(new CalendarResponseDto());
        given(calendarService.getCalendar()).willReturn(responseList);


        mockMvc.perform(get("/api/days"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

        then(calendarService).should(times(1)).getCalendar();
    }

    @Test
    public void testGetTodo() throws Exception {

        List<CalendarResponseDto> responseList = Collections.singletonList(new CalendarResponseDto());
        given(calendarService.getTodo(anyString())).willReturn(responseList);


        mockMvc.perform(get("/api/days/치킨"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

        then(calendarService).should(times(1)).getTodo(anyString());
    }

    @Test
    public void testUpdateCalendar() throws Exception {

        given(calendarService.updateCalendar(anyString(), anyString(), any(CalendarRequestDto.class))).willReturn("치킨");


        mockMvc.perform(put("/api/days/치킨/1234")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"간장맛\",\"contents\":\"20000원\",\"manager\":\"korea@ekswkd.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("치킨"));

        then(calendarService).should(times(1)).updateCalendar(anyString(), anyString(), any(CalendarRequestDto.class));
    }

    @Test
    public void testDeleteCalendar() throws Exception {

        given(calendarService.deleteCalendar(anyString(), anyString())).willReturn("치킨");


        mockMvc.perform(delete("/api/days/치킨/1234"))
                .andExpect(status().isOk())
                .andExpect(content().string("치킨"));

        then(calendarService).should(times(1)).deleteCalendar(anyString(), anyString());
    }

    @ControllerAdvice
    private static class ExceptionControllerAdvice {

        @ExceptionHandler
        public ResponseEntity<String> handleException(IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler
        public ResponseEntity<String> handleException(MethodArgumentNotValidException e) {
            return new ResponseEntity<>(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
