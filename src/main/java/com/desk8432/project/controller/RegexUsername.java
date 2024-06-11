package com.desk8432.project.controller;

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
import java.util.regex.Pattern;

@WebServlet("/insert/regex/username")
public class RegexUsername extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        // 사용자 아이디에 대한 정규식 - 영문 숫자 조합 6~10자리
        String REGEXP_USER_ID = "^[a-z]{1}[a-z0-9]{5,10}+$";  // 아이디 영문 숫자 조합 6 ~ 10

        String username = "asdasd";

        Boolean isCheckUsername = Pattern.matches(REGEXP_USER_ID,username);

        Gson gson = new Gson();
        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("isCheckUsername",isCheckUsername);
        String resultJson = gson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }
}
