package com.desk8432.project.controller.schedule;

import com.desk8432.project.dao.group.InsertGroupDAO;
import com.desk8432.project.dto.group.InsertGroupDTO;
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

@WebServlet("/insertgroup")
public class InsertGroupProcess extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher dispatcher = new Dispatcher();
        String jsonString = dispatcher.getBody(req);

        System.out.println("jsonString = " + jsonString);
        Gson gson = new Gson();
        InsertGroupDTO insertgroupDTO = gson.fromJson(jsonString, InsertGroupDTO.class);

        InsertGroupDAO insertGroupDAO = new InsertGroupDAO();

        System.out.println(insertgroupDTO.toString());
        Gson outGson = new Gson();
        Map<String, String> resultMap = new HashMap<>();

        if (insertGroupDAO.insertGroup(insertgroupDTO)) {
            System.out.println("success");
            resultMap.put("message", "ok");
            // ScriptWriter.alertAndNext(resp, "회원가입되었습니다.", "/index/index");
        } else {
            System.out.println("fail");
            resp.setStatus(400);
            resultMap.put("message", "fail");
            // ScriptWriter.alertAndBack(resp,"알 수 없는 오류가 발생되었습니다.");
        }

        String resultJson = outGson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }
}
