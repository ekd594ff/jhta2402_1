package com.desk8432.project.controller;

import com.desk8432.project.dao.UpdateEmailDAO;
import com.desk8432.project.dao.UpdatePasswordDAO;
import com.desk8432.project.dto.UpdateEmailDTO;
import com.desk8432.project.dto.UpdatePasswordDTO;
import com.desk8432.project.util.Dispatcher;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/update/password")
public class UpdatePassword extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher dispatcher = new Dispatcher();
        String jsonString = dispatcher.getBody(req);

        Gson gson = new Gson();
        UpdatePasswordDTO updatePasswordDTO = gson.fromJson(jsonString, UpdatePasswordDTO.class);
        UpdatePasswordDAO updatePasswordDAO = new UpdatePasswordDAO();

        Gson outGson = new Gson();
        Map<String,String> resultMap = new HashMap<>();
        if (updatePasswordDAO.updatePassword(updatePasswordDTO)) {
            System.out.println("success");
            resultMap.put("message", "ok");
//            ScriptWriter.alertAndNext(resp,"이메일 변경이 완료되었습니다","/index/index");
        } else {
            System.out.println("fail");
            resp.setStatus(400);
            resultMap.put("message", "fail");
//            ScriptWriter.alertAndBack(resp,"이메일 변경이 실패되었습니다");
        }
        String resultJson = outGson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }
}
