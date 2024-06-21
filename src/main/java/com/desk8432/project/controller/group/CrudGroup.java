package com.desk8432.project.controller.group;

import com.desk8432.project.dao.group.InsertGroupDAO;
import com.desk8432.project.dao.member.UpdateDAO;
import com.desk8432.project.dto.group.InsertGroupDTO;
import com.desk8432.project.dto.member.UpdateImageUrlDTO;
import com.desk8432.project.util.CookieManager;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.desk8432.project.util.UploadImage.getImageDTO;
import static com.desk8432.project.util.UploadImage.uploadImage;

@WebServlet("/group/crud")
public class CrudGroup extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = CookieManager.readCookie(req, "username");
        String groupName = req.getParameter("name");
        String content = req.getParameter("content");
        Part image = req.getPart("image");

        Map<String, String> resultMap = new HashMap<>();
        Gson gson = new Gson();

        UpdateImageUrlDTO updateImageUrlDTO = getImageDTO(
                image, username + "_" + groupName, getServletConfig());

        InsertGroupDTO insertGroupDTO = InsertGroupDTO.builder()
                .image_url(updateImageUrlDTO.getImageUrl())
                .name(groupName)
                .content(content)
                .creator(username)
                .build();

        UpdateDAO updateDAO = new UpdateDAO();
        InsertGroupDAO insertGroupDAO = new InsertGroupDAO();

        // group DB에 저장
        CompletableFuture<Void> memberImageUrlUpdateFuture = CompletableFuture.runAsync(() -> {
            if (insertGroupDAO.insertGroup(insertGroupDTO)) {
                System.out.println("Insert group successful");
                resultMap.put("url", "ok");
            }
        });

        // 서버에 이미지 저장
        CompletableFuture<Void> uploadImageFuture = CompletableFuture.runAsync(() -> {
            uploadImage(image, updateImageUrlDTO.getLocation(), updateImageUrlDTO.getFileName()); //이미지 메인서버에 저장
        });

        // File 테이블에 있는지 확인
        CompletableFuture<Boolean> isFileImageFuture = CompletableFuture.supplyAsync(() -> {
            return updateDAO.isFile(updateImageUrlDTO);
        });
        // 있는지 확인 후에 저장 or 수정
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
        });

        try {
            memberImageUrlUpdateFuture.get();
            uploadImageFuture.get();
            isFileImageFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }


        String resultJson = gson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }

}
