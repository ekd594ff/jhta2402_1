package com.desk8432.project.controller;

import com.desk8432.project.dao.MemberDAO;
import com.desk8432.project.dto.MemberDTO;
import com.desk8432.project.utils.ScriptWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/insert")
@MultipartConfig
public class InsertMember extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/insert.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {




//        String salt = BCrypt.gensalt();
//        password = BCrypt.hashpw(password, salt);
//
//        MemberDTO memberDTO = MemberDTO.builder()
//                .username(req.getParameter("username"))
//                .password(req.getParameter("password"))
//                .nickname(req.getParameter("nickname"))
//                .email(req.getParameter("email"))
//                .imageUrl(req.getParameter("imageUrl"))
//                .introduction(req.getParameter("introduction"))
//                .build();



//        MemberDAO memberDAO = new MemberDAO();
//        boolean result = memberDAO.insertMember(memberDTO);
//        if(result = true){
//            ScriptWriter.alertAndNext(resp,"회원가입되었습니다.","/index/index");
//        } else {
//            ScriptWriter.alertAndBack(resp,"알 수 없는 오류가 발생되었습니다. 잠시후 다시 시도해 주세요.");
//        }
    }
}
