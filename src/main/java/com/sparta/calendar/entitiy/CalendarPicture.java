package com.sparta.calendar.entitiy;

import com.sparta.calendar.dto.CalendarPictureRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Getter
@Table(name = "calendarPicture")
@NoArgsConstructor
public class CalendarPicture extends DayStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(name = "data", length = 5242880)
    private byte[] data;

    private String picturename;
    private String extension;
    private int size;

    @OneToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    public CalendarPicture(CalendarPictureRequestDto calendarPictureRequestDto) {
        this.data = calendarPictureRequestDto.getData().getBytes();
    }

    public void setCalendarPicture(MultipartFile picture) {
        try {
            this.data = picture.getBytes();
            this.size = (int) picture.getSize();
            this.extension = picture.getOriginalFilename();
            this.picturename = picture.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
