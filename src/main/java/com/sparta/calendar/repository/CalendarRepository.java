package com.sparta.calendar.repository;

import com.sparta.calendar.entitiy.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    List<Calendar> findAllByOrderByCreatedDesc();
    Calendar findByTodo(String todo);
}
