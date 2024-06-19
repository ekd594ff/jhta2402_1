package com.desk8432.project.controller.member;

import com.desk8432.project.dao.member.InsertDAO;
import com.desk8432.project.dto.member.InsertDTO;
import com.desk8432.project.util.Dispatcher;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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

        System.out.println("jsonString = " + jsonString);
        Gson gson = new Gson();
        InsertDTO insertDTO = gson.fromJson(jsonString, InsertDTO.class);

        String salt = BCrypt.gensalt();
        String hashPW = BCrypt.hashpw(insertDTO.getPassword(), salt);
        insertDTO.setPassword(hashPW);

        InsertDAO insertDAO = new InsertDAO();

        System.out.println(insertDTO.toString());
        Gson outGson = new Gson();
        Map<String,String> resultMap = new HashMap<>(); // map

        Gson outGson01 = new Gson();
        Map<String,String> resultMap01 = new HashMap<>(); // map

        if (insertDAO.insertMember(insertDTO)) {
            System.out.println("success");
            resultMap.put("message", "ok");
//            ScriptWriter.alertAndNext(resp, "회원가입되었습니다.", "/index/index");
        } else {
            System.out.println("fail");
            resp.setStatus(400);
            resultMap.put("message", "fail");
//            ScriptWriter.alertAndBack(resp,"알 수 없는 오류가 발생되었습니다.");
        }

        String resultJson = outGson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }
}
