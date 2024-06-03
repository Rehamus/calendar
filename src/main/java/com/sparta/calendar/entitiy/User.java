package com.sparta.calendar.entitiy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table(name = "calendar_users")
@NoArgsConstructor
public class User extends DayStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false ,unique = true)
    private String nickname;

    @Column( nullable = false ,unique = true)
    private String username;

    @Column( nullable = false)
    private String password;

    @Column( nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    private String refreshToken;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    private List<Reply> replylist =  new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    private List<Calendar> Calendarlist =  new ArrayList<>();

    public User(String nickname, String username, String password, UserRoleEnum roleEnum) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.role = roleEnum;
    }
}
