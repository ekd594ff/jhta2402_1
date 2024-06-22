package com.desk8432.project.controller.group;

import com.desk8432.project.dao.group.DeleteGroupDAO;
import com.desk8432.project.dao.group.FollowGroupDAO;
import com.desk8432.project.dao.group.InsertGroupDAO;
import com.desk8432.project.dao.image.FileDAO;
import com.desk8432.project.dto.group.DeleteGroupDTO;
import com.desk8432.project.dto.group.FollowRequestDTO;
import com.desk8432.project.dto.group.InsertGroupDTO;
import com.desk8432.project.dto.image.InputImageUrlDTO;
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
                image, username, getServletConfig());

        InsertGroupDTO insertGroupDTO = InsertGroupDTO.builder()
                .image_url(updateImageUrlDTO.getImageUrl())
                .name(groupName)
                .content(content)
                .creator(username)
                .build();

        FileDAO fileDAO = new FileDAO();
        InsertGroupDAO insertGroupDAO = new InsertGroupDAO();
        FollowGroupDAO followGroupDAO = new FollowGroupDAO();


        // 서버에 이미지 저장
        CompletableFuture<Void> uploadImageFuture = CompletableFuture.runAsync(() -> {
            uploadImage(image, updateImageUrlDTO.getLocation(), updateImageUrlDTO.getFileName()); //이미지 메인서버에 저장
        });

        // group DB에 저장
        CompletableFuture<Boolean> insertGroupFuture = CompletableFuture.supplyAsync(() -> {
            if (insertGroupDAO.insertGroup(insertGroupDTO)) {
                System.out.println("Insert group successful");
                resultMap.put("url", "ok");
                return true;
            }
            return false;
        });
        // group DB에 저장 되면 검색
        insertGroupFuture.thenApply(res -> {

            long groupId = insertGroupDAO.getGroupId(insertGroupDTO);
            InputImageUrlDTO inputImageUrlDTO = getInputImageUrlDTO(updateImageUrlDTO, groupId);

            // follow 에 본인 저장
            CompletableFuture<Void> insertFollowFuture = CompletableFuture.runAsync(() -> {
                followGroupDAO.addFollowGroup(FollowRequestDTO.builder()
                        .groupID(groupId)
                        .username(username)
                        .build());
            });

            // File 테이블에 있는지 확인
            CompletableFuture<Boolean> isFileImageFuture = CompletableFuture.supplyAsync(() -> {
                return fileDAO.isFileGroup(inputImageUrlDTO);
            });
            // 있는지 확인 후에 저장 or 수정
            isFileImageFuture.thenApply(result -> {
                System.out.println("isFileImage : " + result);
                if (result) {
                    boolean isUpdateFile = fileDAO.updateFileGroup(inputImageUrlDTO);
                    System.out.println("isUpdateFile : " + isUpdateFile);
                    if (isUpdateFile) {
                        resultMap.put("isUpdateFile", "ok");
                    } else {
                        resp.setStatus(400);
                        resultMap.put("isUpdateFile", "fail");
                    }
                    return isUpdateFile;
                } else {
                    boolean isInsertFile = fileDAO.insertFileGroup(inputImageUrlDTO);
                    System.out.println("isInsertFile : " + isInsertFile);
                    if (isInsertFile) {
                        resultMap.put("isInsertFile", "ok");
                    } else {
                        resp.setStatus(400);
                        resultMap.put("isInsertFile", "fail");
                    }
                    return isInsertFile;
                }
            }).exceptionally(ex -> {
                ex.printStackTrace();
                return false;
            });

            try {
                insertFollowFuture.get();
                isFileImageFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            return true;
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return false;
        });


        try {
            insertGroupFuture.get();
            uploadImageFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        String resultJson = gson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        // request 에서 값 받아오기
        String username = CookieManager.readCookie(req, "username");
        Long groupID = Long.parseLong(req.getParameter("id"));
        System.out.println("groupID == " + groupID);
        System.out.println("username == " + username);

        // DAO 에서 DB 삭제 처리
        DeleteGroupDAO deleteGroupDAO = new DeleteGroupDAO();
        boolean result = deleteGroupDAO.deleteGroup(
                DeleteGroupDTO.builder()
                        .groupID(groupID)
                        .creator(username)
                        .build());

        if (result) {
            resp.setStatus(HttpServletResponse.SC_OK);
            System.out.println("성공~!");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println("실패 ㅠㅠ");

        }
    }

    private InputImageUrlDTO getInputImageUrlDTO(UpdateImageUrlDTO updateImageUrlDTO, long groupId) {
        return InputImageUrlDTO.builder()
                .username(updateImageUrlDTO.getUsername())
                .group_id(groupId)
                .image_url(updateImageUrlDTO.getImageUrl())
                .originalName(updateImageUrlDTO.getOriginalName())
                .fileName(updateImageUrlDTO.getFileName())
                .location(updateImageUrlDTO.getLocation())
                .build();
    }
}
