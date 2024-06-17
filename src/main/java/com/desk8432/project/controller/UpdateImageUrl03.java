package com.desk8432.project.controller;

import com.desk8432.project.dao.UpdateDAO;
import com.desk8432.project.dto.UpdateImageUrlDTO;
import com.desk8432.project.util.CookieManager;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import net.coobird.thumbnailator.Thumbnails;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/update/imageUrl")
@MultipartConfig
public class UpdateImageUrl03 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = CookieManager.readCookie(req, "username");
        Part image = req.getPart("image");
//        String username = req.getParameter("username");

        UpdateDAO updateDAO = new UpdateDAO();

        Gson outGson = new Gson();
        Map<String,String> resultMap = new HashMap<>(); //결과값 반환

        UpdateImageUrlDTO updateImageUrlDTO = getImageDTO(image,username);

        CompletableFuture<Void> future01 = CompletableFuture.runAsync(() ->{
            if (updateDAO.updateImageUrl(updateImageUrlDTO)) {
                //member db에 이미지 경로 수정
                resultMap.put("url","ok");
            }
        });
        CompletableFuture<Void> future02 = CompletableFuture.runAsync(() ->{
            uploadImage(image,updateImageUrlDTO.getLocation(), updateImageUrlDTO.getFileName()); //이미지 메인서버에 저장
        });
        CompletableFuture<Boolean> future03 = CompletableFuture.supplyAsync(() -> {
            // 비동기 작업
            return updateDAO.isFile(updateImageUrlDTO); // Boolean 값 반환
        });
        future03.thenApply(result -> {
            if (result) {
                boolean isUpdateFile = updateDAO.updateFile(updateImageUrlDTO);
                if (isUpdateFile) {
                    resultMap.put("isUpdateFile", "ok");
                } else {
                    resp.setStatus(400);
                    resultMap.put("isUpdateFile", "fail");
                }
                return isUpdateFile;
            } else {
                boolean isInsertFile = updateDAO.insertFile(updateImageUrlDTO);
                if (isInsertFile) {
                    resultMap.put("isInsertFile", "ok");
                } else {
                    resp.setStatus(400);
                    resultMap.put("isInsertFile", "fail");
                }
                return isInsertFile;
            }
            // thenApply()는 새로운 값을 반환해야 합니다.
        });
        try {
            future01.get();
            future02.get();
            future03.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        String resultJson = outGson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }

    private UpdateImageUrlDTO getImageDTO(Part image, String username) {
        String formatMonth = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMM")
        );
        String formatDay = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd")
        );
        String imgFolder = "/image" +"/"+ formatMonth + "/" + formatDay;

        String imgFolderPath = getServletConfig().getServletContext().getRealPath(imgFolder);
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
    private void uploadImage(Part image, String imgFolderPath, String uploadUrl) {
        // 별도의 이미지 저장 장소 없이 메인 서버에 저장
        try {
            Files.createDirectories(Paths.get(imgFolderPath));
            image.write(uploadUrl);
            Thumbnails.of(uploadUrl).size(100, 100).toFile(uploadUrl);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("success");
    }
}
