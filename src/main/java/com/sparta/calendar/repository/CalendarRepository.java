package com.sparta.calendar.repository;

import com.sparta.calendar.entitiy.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

}
