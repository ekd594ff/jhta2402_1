package com.desk8432.project.controller.group;

import com.desk8432.project.dao.group.DeleteGroupDAO;
import com.desk8432.project.dao.group.InsertGroupDAO;
import com.desk8432.project.dao.group.UpdateGroupDAO;
import com.desk8432.project.dao.member.LoginDAO;
import com.desk8432.project.dao.member.UpdateDAO;
import com.desk8432.project.dto.group.DeleteGroupDTO;
import com.desk8432.project.dto.group.FollowRequestDTO;
import com.desk8432.project.dto.group.GroupDTO;
import com.desk8432.project.dto.group.InsertGroupDTO;
import com.desk8432.project.dto.member.DeleteMemberDTO;
import com.desk8432.project.dto.member.UpdateImageUrlDTO;
import com.desk8432.project.util.CookieManager;
import com.desk8432.project.util.Dispatcher;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        String username = CookieManager.readCookie(req, "username");
        String groupname = req.getParameter("name");
        String content = req.getParameter("content");
        Long groupID = 0L;
        // Part image = req.getPart("image");
        if (req.getParameter("id") != null) {
            groupID = Long.parseLong(req.getParameter("id"));
            System.out.println("groupID == " + groupID);
        }
        System.out.println("username == " + username);
        System.out.println("groupname == " + groupname);
        System.out.println("content == " + content);
        // System.out.println("image == " + image);

        // 어떤 값이 들어오는지 판별 -> dao로 db에 적용
        UpdateGroupDAO updateGroupDAO = new UpdateGroupDAO();

        if (content != null) {
            // 컨텐츠 변경
            boolean contentResult = updateGroupDAO.updateContent(
                    GroupDTO.builder()
                            .id(groupID)
                            .content(content)
                            .build());

            if (contentResult) {
                resp.setStatus(HttpServletResponse.SC_OK);
                System.out.println("update content 성공~!");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                System.out.println("update content 실패 ㅠㅠ");

            }

        } else if (groupname != null) {
            // 그룹 이름 변경
            boolean groupNameResult = updateGroupDAO.updateGroupName(
                    GroupDTO.builder()
                            .id(groupID)
                            .groupname(groupname)
                            .build());

            if (groupNameResult) {
                resp.setStatus(HttpServletResponse.SC_OK);
                System.out.println("update group name 성공~!");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                System.out.println("update group name 실패 ㅠㅠ");

            }

        }
        // 이미지 변경


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
}
