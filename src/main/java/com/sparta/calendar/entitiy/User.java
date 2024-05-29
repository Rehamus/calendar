package com.sparta.calendar.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "calendar_users")
@NoArgsConstructor
@AllArgsConstructor
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


    public User(String nickname, String username, String password, UserRoleEnum roleEnum) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.role = roleEnum;
    }
}
