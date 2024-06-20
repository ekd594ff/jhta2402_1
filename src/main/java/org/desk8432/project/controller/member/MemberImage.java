package org.desk8432.project.controller.member;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/member/image")
@MultipartConfig
public class MemberImage extends HttpServlet {

    // { username: "", image: file } 요청 / 성공시 { imgUrl : "" } 실패시 { "error" : "" }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        Part image = req.getPart("image");

        Map<String, String> returnMap = new HashMap<>();
        Gson gson = new Gson();
        resp.setContentType("application/json");

        // 이미지 업로드
        String uploadUrl = uploadImage(image, username);

        if (uploadUrl == null) {
            resp.setStatus(500);
            returnMap.put("error", "Image Upload Failed");
        } else {
            returnMap.put("imgUrl", uploadUrl);
        }
        resp.getWriter().println(gson.toJson(returnMap));
    }

    // 폴더경로/유저이름_날짜.확장자
    private String uploadImage(Part image, String username) {
        String formatMonth = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMM")
        );
        String imgFolder = "/image" +"/"+ formatMonth;

        String imgFolderPath = getServletConfig().getServletContext().getRealPath(imgFolder);
        System.out.println("imgFolderPath = " + imgFolderPath);
        String formatNow = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("ddhhmmss")
        );

        System.out.println("formatMonth = " + formatMonth);
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
