package com.desk8432.project.controller;

import com.desk8432.project.dao.UpdateEmailDAO;
import com.desk8432.project.dao.UpdateNicknameDAO;
import com.desk8432.project.dto.UpdateEmailDTO;
import com.desk8432.project.dto.UpdateNicknameDTO;
import com.desk8432.project.util.Dispatcher;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/update/nickname")
public class UpdateNickname extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher dispatcher = new Dispatcher();
        String jsonString = dispatcher.getBody(req);

        Gson gson = new Gson();
        UpdateNicknameDTO updateNicknameDTO = gson.fromJson(jsonString, UpdateNicknameDTO.class);
        UpdateNicknameDAO updateNicknameDAO = new UpdateNicknameDAO();

        if (updateNicknameDAO.updateNickname(updateNicknameDTO)) {
            System.out.println("success");
//            ScriptWriter.alertAndNext(resp,"이메일 변경이 완료되었습니다","/index/index");
        } else {
            System.out.println("fail");
//            ScriptWriter.alertAndBack(resp,"이메일 변경이 실패되었습니다");
        }
    }
}
