package com.sparta.calendar.entitiy;

import com.sparta.calendar.dto.CalendarRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "contents", nullable = false , length = 500)
    private String contents;

    @Column(name = "manager", nullable = false)
    @Email(message = "올바른 이메일형식이 아닙니다.")
    private String manager;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "calendar_id")
    private List<Reply> replylist =  new ArrayList<>();


    public Calendar(CalendarRequestDto requestDto) {
        this.todo = requestDto.getTodo();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.manager = requestDto.getManager();
        this.password = requestDto.getPassword();
    }


    public void update(CalendarRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.manager = requestDto.getManager();
    }
}
