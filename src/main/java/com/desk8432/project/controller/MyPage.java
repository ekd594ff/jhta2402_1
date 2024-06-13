package com.desk8432.project.controller;

import com.desk8432.project.dao.MyPageDAO;
import com.desk8432.project.dto.MyPageDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/member/info")
public class MyPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        MyPageDAO myPageDAO = new MyPageDAO();
        MyPageDTO myPageDTO = new MyPageDTO();
        myPageDTO.setUsername(username);
        MyPageDTO infoMemberDTO = myPageDAO.loginMember(myPageDTO);
        req.setAttribute("infoMemberDTO", infoMemberDTO);
        req.getRequestDispatcher("/WEB-INF/mypage.jsp")
                .forward(req,resp);
    }
}
