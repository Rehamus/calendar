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

    @Column(name = "reply_username")
    private String reply_username;

    @Column(name = "reply_content")
    private String reply_content;

    @ManyToOne
    @JoinColumn(name = "calendar_id" ,insertable = false, updatable = false)
    private Calendar calendar;


    public Reply(ReplyRequestDto replydto) {
        this.reply_username = replydto.getReply_username();
        this.reply_content = replydto.getReply_content();
    }

    public void reply_update(ReplyRequestDto replydto) {
        this.reply_content = replydto.getReply_content();
    }
}
