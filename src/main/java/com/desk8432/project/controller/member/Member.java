package com.desk8432.project.controller.member;

import com.desk8432.project.dao.member.MemberInfoDAO;
import com.desk8432.project.dto.member.MyPageDTO;
import com.desk8432.project.util.CookieManager;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/memberinfo")
public class Member extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = CookieManager.readCookie(req, "username");
        Gson gson = new Gson();
        Map<String, Object> jsonMap = new HashMap<>();
        if(username == null) {
            jsonMap.put("msg","");
            resp.setStatus(204);
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(jsonMap));
        } else {
            MemberInfoDAO memberInfoDAO = new MemberInfoDAO();
            MyPageDTO memberInfo = memberInfoDAO.getMemberInfo(username);
            jsonMap.put("msg","login");

            Map<String, Object> dataJsonMap = new HashMap<>();
            dataJsonMap.put("username", memberInfo.getUsername());
            dataJsonMap.put("nickname", memberInfo.getNickname());
            dataJsonMap.put("email", memberInfo.getEmail());
            dataJsonMap.put("introduction", memberInfo.getIntroduction());
            dataJsonMap.put("profileImgUrl",memberInfo.getImage_url());
            dataJsonMap.put("createdAt", memberInfo.getCreated_at().toString());
            jsonMap.put("data", dataJsonMap);

            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(jsonMap));
        }
    }
}
