package com.desk8432.project.controller;

import com.desk8432.project.dao.MyPageDAO;
import com.desk8432.project.dto.MyPageDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/mypage")
public class MyPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/mypage.jsp")
                .forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Dummy authentication check
        if ("user".equals(username) && "password".equals(password)) {
            Cookie authCookie = new Cookie("authCookie", "validAuthToken");
            authCookie.setHttpOnly(true);
            authCookie.setMaxAge(60 * 60); // 1 hour
            response.addCookie(authCookie);
            response.sendRedirect(request.getContextPath() + "/mypage");

        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Invalid%20credentials");
        }
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("authCookie".equals(cookie.getName()) && "validAuthToken".equals(cookie.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }
}
