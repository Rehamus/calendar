package com.sparta.calendar.service;

import com.sparta.calendar.dto.CalendarResponseDto;
import com.sparta.calendar.dto.ReplyRequestDto;
import com.sparta.calendar.dto.ReplyResponseDto;
import com.sparta.calendar.entitiy.Calendar;
import com.sparta.calendar.entitiy.Reply;
import com.sparta.calendar.repository.CalendarRepository;
import com.sparta.calendar.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {


    private final ReplyRepository replyRepository;
    private final CalendarService calendarService;


    @Transactional
    public ReplyResponseDto createreply(String todo, ReplyRequestDto replydto) {
        Calendar calendar = calendarService.findCalendarTodo(todo);
        Reply reply = new Reply(replydto);
        calendar.getReplylist().add(reply);
        Reply savereply = replyRepository.save( reply );

        return new ReplyResponseDto( savereply );

    }

    @Transactional
    public String  updateReply(String todo, ReplyRequestDto replydto, Long reply_id) {
        Reply reply = getReply( todo, reply_id );
        reply.reply_update(replydto);
        return "댓글 수정 완료";

    }

    public String deleteReply(String todo, Long reply_id) {
        Reply reply = getReply( todo, reply_id );
        replyRepository.delete( reply );
        return "삭제 완료";
    }





    public List<ReplyResponseDto> getReply(String todo) {
        List<CalendarResponseDto> calendarList = calendarService.getTodo( todo );

        if (calendarList.isEmpty()) {
            throw new IllegalArgumentException( "댓글이 없습니다.");
        }

        CalendarResponseDto calendar = calendarList.get( 0 );

        return replyRepository.findAll().stream()
                .filter( R -> R.getCalendar().getId().equals( calendar.getId() ) )
                .map( ReplyResponseDto ::new ).toList();
    }


    private Reply getReply(String todo, Long reply_id) {
        List<ReplyResponseDto> responseDtoList = getReply( todo ).stream()
                .filter( C -> C.getId().equals( reply_id ) )
                .toList();

        if (responseDtoList.isEmpty()) {
            throw new IllegalArgumentException( "해당 댓글이 없습니다.");
        }

        return replyRepository.findById( reply_id )
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 댓글이 존재하지 않습니다."));
    }


}
