package com.sparta.calendar;

import com.sparta.calendar.entitiy.Calendar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class CalendarApplicationTests {

    @Test
    void contextLoads() {
    }

}
