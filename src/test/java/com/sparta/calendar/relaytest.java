/*
package com.sparta.calendar;

import com.sparta.calendar.entitiy.Calendar;
import com.sparta.calendar.entitiy.Reply;
import com.sparta.calendar.repository.CalendarRepository;
import com.sparta.calendar.repository.RelyRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
public class relaytest {

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    RelyRepository relayRepository;

    @Test
    @Rollback(false)
    public void relaytest() {

        Calendar calendar = new Calendar();
        calendar.setTodo( "relay test" );
        calendar.setTitle( "Relay test" );
        calendar.setContents( "Relay test" );
        calendar.setManager( "ZZZ@ZZZ.com" );
        calendar.setPassword( "밥공기" );

        Reply reply = new Reply();
        reply.setReply_username( "Relay test" );
        reply.setReply_content( "Relay test" );

        Reply reply2 = new Reply();
        reply2.setReply_username( "Relay test2" );
        reply2.setReply_content( "Relay test2" );

        calendar.getReplylist().add( reply );
        calendar.getReplylist().add( reply2 );


        relayRepository.save( reply );
        relayRepository.save( reply2 );
        calendarRepository.save( calendar );


    }

}
*/
