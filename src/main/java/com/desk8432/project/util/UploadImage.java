package com.desk8432.project.util;

import com.desk8432.project.dto.member.UpdateImageUrlDTO;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.Part;
import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UploadImage {

    public static UpdateImageUrlDTO getImageDTO(Part image, String username, ServletConfig servletConfig) {
        String formatMonth = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMM")
        );
        String formatDay = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd")
        );
        String imgFolder = "/image" +"/"+ formatMonth + "/" + formatDay;

        String imgFolderPath = servletConfig.getServletContext().getRealPath(imgFolder);
        System.out.println("imgFolderPath = " + imgFolderPath);
        String formatNow = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("hhmmss")
        );

        System.out.println("formatMonth = " + formatMonth);
        String fileName = image.getSubmittedFileName();
        String extension = fileName.substring(fileName.lastIndexOf("."));

        String uploadUrl = imgFolderPath + "/" + username + "_" + formatNow + extension;
        String returnUrl = imgFolder + "/" + username + "_" + formatNow + extension;
//        String filename = username + "_" + formatNow + extension;

        UpdateImageUrlDTO updateImageUrlDTO
                = UpdateImageUrlDTO.builder()
                .username(username)
                .originalName(fileName)
                .location(imgFolderPath)
                .imageUrl(returnUrl)
                .fileName(uploadUrl)
                .build();
        return updateImageUrlDTO;
    }

    //폴더 경로/유저이름_날짜.확장자 이미지
    public static void uploadImage(Part image, String imgFolderPath, String uploadUrl) {
        // 별도의 이미지 저장 장소 없이 메인 서버에 저장
        try {
            Files.createDirectories(Paths.get(imgFolderPath));
            image.write(uploadUrl);
            Thumbnails.of(uploadUrl).size(250, 250).toFile(uploadUrl);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("success");
    }
}
