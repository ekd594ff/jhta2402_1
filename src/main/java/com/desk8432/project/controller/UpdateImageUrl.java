package com.desk8432.project.controller;

import com.desk8432.project.dao.UpdateDAO;
import com.desk8432.project.dto.UpdateEmailDTO;
import com.desk8432.project.dto.UpdateImageUrlDTO;
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

@WebServlet("/update/imageUrl")
public class UpdateImageUrl extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher dispatcher = new Dispatcher();
        String jsonString = dispatcher.getBody(req);

        Gson gson = new Gson();
        UpdateImageUrlDTO updateImageUrlDTO = gson.fromJson(jsonString, UpdateImageUrlDTO.class);
        UpdateDAO updateDAO = new UpdateDAO();

        String username = CookieManager.readCookie(req, "username");
        updateImageUrlDTO.setUsername(username);

        Gson outGson = new Gson();
        Map<String,String> resultMap = new HashMap<>();

        if (updateDAO.updateImageUrl(updateImageUrlDTO)) {System.out.println("success");
            resultMap.put("message", "ok");
//            ScriptWriter.alertAndNext(resp,"이미지 변경이 완료되었습니다","/index/index");
        } else {
            System.out.println("fail");
            resp.setStatus(400);
            resultMap.put("message", "fail");
//            ScriptWriter.alertAndBack(resp,"이미지 변경이 실패되었습니다");
        }

        String resultJson = outGson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }

    //폴더 경로/유저이름_날짜.확장자 이미지
    private String uploadImage(Part image, String username) {
        String imgFolder = "/image";
        String imgFolderPath = getServletConfig().getServletContext().getRealPath(imgFolder);

        String formatNow = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss")
        );

        String fileName = image.getSubmittedFileName();
        String extension = fileName.substring(fileName.lastIndexOf("."));

        String uploadUrl = imgFolderPath + "/" + username + "_" + formatNow + extension;
        String returnUrl = imgFolder + "/" + username + "_" + formatNow + extension;

        // 별도의 이미지 저장 장소 없이 메인 서버에 저장
        try {
            Files.createDirectories(Paths.get(imgFolderPath));
            image.write(uploadUrl);
            Thumbnails.of(uploadUrl).size(100, 100).toFile(uploadUrl);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return returnUrl;
    }
}
