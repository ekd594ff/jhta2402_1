package com.desk8432.project.controller;

import com.desk8432.project.dao.MemberDAO;
import com.desk8432.project.dto.MemberDTO;
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

        MemberDTO insertMemberDTO =
                MemberDTO.builder()
                        .username("test03")
                        .email("asd@naver.com")
                        .password("1234")
                        .nickname("kjh")
                        .introduction("")
                        .imageUrl("")
                        .build();
        MemberDAO memberDAO1 = new MemberDAO();

        boolean isInsert =memberDAO1.insertMember(insertMemberDTO);
        System.out.println("isInsert = " + isInsert);
    }
}
