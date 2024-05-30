package com.sparta.calendar.service;

import com.sparta.calendar.dto.CalendarResponseDto;
import com.sparta.calendar.dto.ReplyRequestDto;
import com.sparta.calendar.dto.ReplyResponseDto;
import com.sparta.calendar.entitiy.Calendar;
import com.sparta.calendar.entitiy.Reply;
import com.sparta.calendar.entitiy.User;
import com.sparta.calendar.jwt.JwtUtil;
import com.sparta.calendar.repository.CalendarRepository;
import com.sparta.calendar.repository.ReplyRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sparta.calendar.entitiy.UserRoleEnum.ADMIN;

@Service
@RequiredArgsConstructor
public class ReplyService {


    private final ReplyRepository replyRepository;
    private final CalendarService calendarService;
    private final UserService userService;
    private final JwtUtil jwtUtil;


    //댓글 생성
    @Transactional
    public ReplyResponseDto createreply(HttpServletRequest request,String todo, ReplyRequestDto replydto){
        Calendar calendar = calendarService.findCalendarTodo(todo);
        User user = jwtUtil.gettokenUser( request );
        Reply reply = new Reply(replydto , user);
        calendar.getReplylist().add(reply);
        user.getReplylist().add(reply);
        Reply savereply = replyRepository.save( reply );
        return new ReplyResponseDto( savereply ,user );

    }

    //댓글 수정
    @Transactional
    public String  updateReply(HttpServletRequest request, String todo, ReplyRequestDto replydto, Long reply_id) {
        Reply reply = ReplyUsercheck( request, todo, reply_id );
        reply.reply_update(replydto);
        return "댓글 수정 완료";
    }

    //댓글 삭제
    @Transactional
    public String deleteReply(HttpServletRequest request,String todo, Long reply_id) {
        Reply reply = ReplyUsercheck( request, todo, reply_id );
        replyRepository.delete( reply );

        return "삭제 완료";
    }




    //댓글 조회
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


    // 댓글 가져오기
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


    // 사용자 검증
    private Reply ReplyUsercheck(HttpServletRequest request, String todo, Long reply_id) {
        User user = jwtUtil.gettokenUser( request );
        Reply reply = getReply( todo, reply_id );
        if (user.getRole().equals( ADMIN )){
            return reply;
        }

        if(! user.getUsername().equals( reply.getReply_username()) ){
            throw new IllegalArgumentException("작성자만 삭제/수정 할 수 있습니다.");
        }
        return reply;
    }


}
