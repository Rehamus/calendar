package com.sparta.calendar.entitiy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class Album {
}



@Getter
@Setter
@ToString
 class UploadVO {
    private String nmae;
    private MultipartFile file;

}


@Getter
@Setter
@ToString
    class MultiUploadVO {
    private List<UploadVO> list;
    
}