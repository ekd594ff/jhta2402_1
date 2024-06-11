package com.desk8432.project.controller;

import com.desk8432.project.dao.MemberDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/insert")
public class TestDAO extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MemberDAO memberDAO = new MemberDAO();
        String testUsername = "KSB02";
        String testNickname = "KSB";
        String testEmail = "test02@test.com";
        boolean duplicateUsername = memberDAO.duplicateUsername(testUsername);
        boolean duplicateNickname = memberDAO.duplicateNickname(testNickname);
        boolean duplicateEmail = memberDAO.duplicateEmail(testEmail);

        System.out.println("duplicateEmail = " + duplicateEmail);
        System.out.println("duplicateNickname = " + duplicateNickname);
        System.out.println("duplicateUsername = " + duplicateUsername);
    }
}
