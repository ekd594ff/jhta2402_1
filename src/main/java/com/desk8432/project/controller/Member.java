package com.desk8432.project.controller;

import com.desk8432.project.dao.MemberDAO;
import com.desk8432.project.dto.MemberDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/member")
public class Member extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        MemberDAO memberDAO = new MemberDAO();
//        MemberDTO memberDTO = memberDAO.getMember();
//        req.setAttribute("memberDTO", memberDTO);
//        req.getRequestDispatcher("/WEB-INF/member.jsp").forward(req, resp);
    }
}
