package com.desk8432.project.controller.member;

import com.desk8432.project.dao.image.FileDAO;
import com.desk8432.project.dao.member.UpdateDAO;
import com.desk8432.project.dto.member.UpdateImageUrlDTO;
import com.desk8432.project.util.CookieManager;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.desk8432.project.util.UploadImage.getImageDTO;
import static com.desk8432.project.util.UploadImage.uploadImage;

@WebServlet("/update/imageUrl")
@MultipartConfig
public class UpdateImageUrl03 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = CookieManager.readCookie(req, "username");
        Part image = req.getPart("image");

//        String username = req.getParameter("username");
        UpdateDAO updateDAO = new UpdateDAO();
        FileDAO fileDAO = new FileDAO();

        Gson outGson = new Gson();
        Map<String,String> resultMap = new HashMap<>(); //결과값 반환

        UpdateImageUrlDTO updateImageUrlDTO = getImageDTO(image,username, getServletConfig());

        CompletableFuture<Void> memberImageUrlUpdateFuture = CompletableFuture.runAsync(() ->{
            if (updateDAO.updateImageUrl(updateImageUrlDTO)) {
                //member db에 이미지 경로 수정
                resultMap.put("url","ok");
            }
        });
        CompletableFuture<Void> uploadImageFuture = CompletableFuture.runAsync(() -> {
            uploadImage(image, updateImageUrlDTO.getImgFolderPath(),updateImageUrlDTO.getUploadUrl());
//            uploadImage(image,updateImageUrlDTO.getLocation(), updateImageUrlDTO.getFileName()); //이미지 메인서버에 저장
        });
        CompletableFuture<Boolean> isFileImageFuture = CompletableFuture.supplyAsync(() -> {
            // 비동기 작업
            return fileDAO.isFile(updateImageUrlDTO); // Boolean 값 반환
        });
        isFileImageFuture.thenApply(result -> {
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
            memberImageUrlUpdateFuture.get();
            uploadImageFuture.get();
            isFileImageFuture.get();
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

}
