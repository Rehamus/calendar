package com.sparta.calendar.entitiy;

import com.sparta.calendar.dto.CalendarRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "calendar")
@NoArgsConstructor
@AllArgsConstructor
public class Calendar extends DayStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "todo",unique = true, nullable = false)
    private String todo;

    private String username;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "contents", nullable = false , length = 500)
    private String contents;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "calendar_id")
    private List<Reply> replylist =  new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id" ,insertable = false, updatable = false)
    private User user;

    @OneToOne(mappedBy = "calendar", cascade = CascadeType.ALL)
    private CalendarPicture calendarPicture;

    public Calendar(CalendarRequestDto requestDto ,User user) {
        this.todo = requestDto.getTodo();
        this.username = user.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public Calendar(CalendarRequestDto requestDto, User user, CalendarPicture calendarPicture) {
        this.todo = requestDto.getTodo();
        this.username = user.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.calendarPicture = calendarPicture;
    }

    public void update(CalendarRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}
