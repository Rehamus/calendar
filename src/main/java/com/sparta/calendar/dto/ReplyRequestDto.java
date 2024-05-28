package com.sparta.calendar.dto;

import com.sparta.calendar.entitiy.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReplyRequestDto {

    private String reply_username;
    private String reply_content;
    private Long calendar_id;


}

