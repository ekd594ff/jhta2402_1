package com.desk8432.project.controller;

import com.desk8432.project.dao.UpdateEmailDAO;
import com.desk8432.project.dao.UpdateImageUrlDAO;
import com.desk8432.project.dto.UpdateEmailDTO;
import com.desk8432.project.dto.UpdateImageUrlDTO;
import com.desk8432.project.util.Dispatcher;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/update/imageUrl")
public class UpdateImageUrl extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher dispatcher = new Dispatcher();
        String jsonString = dispatcher.getBody(req);

        Gson gson = new Gson();
        UpdateImageUrlDTO updateImageUrlDTO = gson.fromJson(jsonString, UpdateImageUrlDTO.class);
        UpdateImageUrlDAO updateImageUrlDAO = new UpdateImageUrlDAO();

        if (updateImageUrlDAO.updateImageUrl(updateImageUrlDTO)) {
            System.out.println("success");
//            ScriptWriter.alertAndNext(resp,"이미지 변경이 완료되었습니다","/index/index");
        } else {
            System.out.println("fail");
//            ScriptWriter.alertAndBack(resp,"이미지 변경이 실패되었습니다");
        }
    }
}
