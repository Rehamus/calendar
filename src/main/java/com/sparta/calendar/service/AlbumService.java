package com.sparta.calendar.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class AlbumService {

    public String uploadFile(MultipartFile file, String uploadFolder) {
        String fileimage = file.getContentType();
        if (!fileimage.contains("image")) {
            throw new IllegalArgumentException("이미지 파일이 아닙니다.");
        }
        String fileRealName = file.getOriginalFilename();
        long size = file.getSize();

        String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."), fileRealName.length());

        UUID uuid = UUID.randomUUID();
        String[] uuids = uuid.toString().split("-");
        String uniqueName = uuids[0];

        File saveFile = new File(uploadFolder + "\\" + uniqueName + fileExtension);

        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // 업로드 실패시 null 반환
        }
        return saveFile.getAbsolutePath(); // 파일의 저장 경로 반환
    }
}
