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

@WebServlet("/insert/regex/password")
public class RegexPassword extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 3. 사용자 패스워드에 대한 정규식 - 대소문자 + 숫자 + 특수문자 조합으로 10 ~ 128자리로 정의 한다.
        String REGEXP_USER_PW_TYPE1 = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{10,128}+$";
        String password = "1231231ss";

        Boolean isCheckPassword = Pattern.matches(REGEXP_USER_PW_TYPE1, password);

        Gson gson = new Gson();
        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("isCheckPassword",isCheckPassword);
        String resultJson = gson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }
}
