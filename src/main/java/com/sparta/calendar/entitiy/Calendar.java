package com.sparta.calendar.entitiy;

import com.sparta.calendar.dto.CalendarRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "calendar")
@NoArgsConstructor
public class Calendar extends DayStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "todo",unique = true, nullable = false)
    private String todo;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "contents", nullable = false)
    private String contents;
    @Column(name = "manager", nullable = false)
    private String manager;
    @Column(name = "password", nullable = false)
    private String password;

    public Calendar(CalendarRequestDto requestDto) {
        this.todo = requestDto.getTodo();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.manager = requestDto.getManager();
        this.password = requestDto.getPassword();
    }

    public void update(CalendarRequestDto requestDto) {
        this.todo = requestDto.getTodo();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.manager = requestDto.getManager();
        this.password = requestDto.getPassword();
    }
}
