package com.sparta.calendar.entitiy;

import com.sparta.calendar.dto.CalendarPictureRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "calendarPicture")
@NoArgsConstructor
@AllArgsConstructor
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
}
