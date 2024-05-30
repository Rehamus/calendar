package com.sparta.calendar.entitiy;


import com.sparta.calendar.dto.ReplyRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reply")
@NoArgsConstructor
@AllArgsConstructor
public class Reply extends DayStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String reply_username;

    @Column(name = "content")
    private String reply_content;

    @ManyToOne
    @JoinColumn(name = "calendar_id" ,insertable = false, updatable = false)
    private Calendar calendar;

    @ManyToOne
    @JoinColumn(name = "user_id" ,insertable = false, updatable = false)
    private User user;


    public Reply(ReplyRequestDto replydto , User user) {
        this.reply_username = user.getUsername();
        this.reply_content = replydto.getReply_content();
    }

    public void reply_update(ReplyRequestDto replydto) {
        this.reply_content = replydto.getReply_content();
    }
}
