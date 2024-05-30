package com.sparta.calendar.controller;


import com.sparta.calendar.dto.ReplyRequestDto;
import com.sparta.calendar.dto.ReplyResponseDto;
import com.sparta.calendar.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("days/{todo}/reply")
    @Operation(summary = "댓글 쓰기")
    public ReplyResponseDto reply(HttpServletRequest request, @PathVariable String todo ,@RequestBody ReplyRequestDto replydto) {
        return replyService.createreply(request,todo,replydto);
    }

    @GetMapping("days/{todo}/reply")
    @Operation(summary = "댓글 조회")
    public List<ReplyResponseDto> getReply(@PathVariable String todo) {
        return replyService.getReply(todo);
    }

    @PutMapping("days/{todo}/{reply_id}")
    @Operation(summary = "댓글 수정")
    public String updateReply(
            HttpServletRequest request,
            @PathVariable String todo,
            @RequestBody ReplyRequestDto replydto,
            @PathVariable Long reply_id) {
        return replyService.updateReply(request,todo,replydto,reply_id);
    }

    @DeleteMapping("days/{todo}/{reply_id}")
    @Operation(summary = "댓글 삭제")
    public String deleteReply(
            HttpServletRequest request,
            @PathVariable String todo,
            @PathVariable Long reply_id) {
        return replyService.deleteReply(request,todo,reply_id);
    }

}
