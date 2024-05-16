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

    public CalendarResponseDto createCalendar(CalendarRequestDto requestDto) {
        // RequestDto -> Entity
        Calendar calendar = new Calendar( requestDto);

        // DB 저장
        Calendar saveCalendar = calendarRepository.save(calendar);

        // Entity -> ResponseDto
        CalendarResponseDto calendarResponseDto = new CalendarResponseDto(saveCalendar);

        return calendarResponseDto;
    }
    public List<CalendarResponseDto> getCalendar() {
        // DB 조회
        return calendarRepository.findAll().stream().map(CalendarResponseDto::new).toList();
    }

    @Transactional
    public Long updateCalendar(Long id, CalendarRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Calendar calendar = findCalendar(id);

        // memo 내용 수정
        calendar.update(requestDto);

        return id;
    }

    public Long deleteCalendar(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Calendar calendar = findCalendar(id);

        // memo 삭제
        calendarRepository.delete(calendar);

        return id;
    }

    private Calendar findCalendar(Long id) {
        return calendarRepository.findById(id).orElseThrow(() ->
                                                               new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
