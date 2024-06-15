package com.desk8432.project.controller;

import com.desk8432.project.util.CookieManager;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/logout")
public class LogOutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,String> resultMap = new HashMap<>();
        Gson gson = new Gson();
        HttpSession session = req.getSession(false);
        if(session!=null) {
            session.setAttribute("member", null);
        }



        CookieManager.deleteCookie(resp, "username");

        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(resultMap));
        resp.setStatus(200);
        resultMap.put("message", "ok");
    }
}

