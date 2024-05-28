package com.sparta.calendar.dto;

import com.sparta.calendar.entitiy.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReplyResponseDto {
    private Long id;
    private String reply_username;
    private String reply_content;
    private LocalDateTime created;


    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.reply_username = reply.getReply_username();
        this.reply_content = reply.getReply_content();
        this.created = reply.getCreated();
    }

}

