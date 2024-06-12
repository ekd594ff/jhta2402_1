package com.desk8432.project.controller;

import com.desk8432.project.dao.InsertDAO;
import com.desk8432.project.dao.MemberDAO;
import com.desk8432.project.dto.InsertDTO;
import com.desk8432.project.dto.MemberDTO;
import com.desk8432.project.util.Dispatcher;
import com.desk8432.project.utils.ScriptWriter;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/insert")
public class InsertMember extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/insert.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher dispatcher = new Dispatcher();
        String jsonString = dispatcher.getBody(req);
        Gson gson = new Gson();
        InsertDTO insertDTO = gson.fromJson(jsonString, InsertDTO.class);
        InsertDAO insertDAO = new InsertDAO();
        insertDTO.setImageUrl("test.jpg"); // imageUrl test용
        if (insertDAO.insertMember(insertDTO)) {
            System.out.println("success");
//            ScriptWriter.alertAndNext(resp, "회원가입되었습니다.", "/index/index");
        } else {
            System.out.println("fail");
//            ScriptWriter.alertAndBack(resp,"알 수 없는 오류가 발생되었습니다.");
        }
    }
}
