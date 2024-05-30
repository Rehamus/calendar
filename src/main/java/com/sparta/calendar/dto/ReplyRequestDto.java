package com.sparta.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReplyRequestDto {

    private String reply_username;
    private String reply_content;
    private Long calendar_id;
    private String user;

}

