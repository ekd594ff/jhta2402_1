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


@WebServlet("/insert/regex/email")
public class RegexEmail extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String REGEXP_LIGHT_USER_EMAIL = "^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\.[a-z]+$";  // 언더바(_), 하이픈(-) 제외
        String email = "asdsad@sada.com";

        Boolean isCheckEmail = Pattern.matches(REGEXP_LIGHT_USER_EMAIL,email);

        Gson gson = new Gson();
        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("isCheckEmail",isCheckEmail);
        String resultJson = gson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }
}
