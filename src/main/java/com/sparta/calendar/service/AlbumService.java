package com.sparta.calendar.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class AlbumService {

    //파일 업로드
    public String uploadFile(MultipartFile file, String uploadFolder) {
        String fileimage = file.getContentType();
        if (!fileimage.contains("image")) {
            throw new IllegalArgumentException("이미지 파일이 아닙니다.");
        }
        String fileRealName = file.getOriginalFilename();
        long size = file.getSize();

        String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."), fileRealName.length());

        // 난수 생성
        UUID uuid = UUID.randomUUID();
        String[] uuids = uuid.toString().split("-");
        String uniqueName = uuids[0];

        // 파일 등록
        File saveFile = new File(uploadFolder + "\\" + uniqueName + fileExtension);

        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("저장에 실패했습니다.");
        }
        // 지정위치에 파일저장
        return saveFile.getAbsolutePath();
    }
}
