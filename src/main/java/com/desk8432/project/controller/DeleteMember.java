package com.desk8432.project.controller;

import com.desk8432.project.dao.DeleteMemberDAO;
import com.desk8432.project.dao.MemberDAO;
import com.desk8432.project.dto.DeleteMemberDTO;
import com.desk8432.project.dto.InsertDTO;
import com.desk8432.project.dto.MemberDTO;
import com.desk8432.project.util.Dispatcher;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/member/delete")
public class DeleteMember extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher dispatcher = new Dispatcher();
        String jsonString = dispatcher.getBody(req);

        Gson gson = new Gson();
        DeleteMemberDTO deleteMemberDTO = gson.fromJson(jsonString, DeleteMemberDTO.class);
        //json으로 받고 DTO에 입력

//        MemberDAO memberDAO = new MemberDAO();
//        MemberDTO loginMemberDTO = memberDAO.loginMember(deleteMemberDTO.getUsername()); //아이디와 같은 비밀번호 아이디 dto 값 얻기
//        if (loginMemberDTO != null) {
//            String encodedPassword =loginMemberDTO.getPassword();
//            if (BCrypt.checkpw(deleteMemberDTO.getPassword(), loginMemberDTO.getPassword())) {
//                // 비밀번호 맞음
//            } else {
//                //비밀번호 틀림
//            }
//        }

        DeleteMemberDAO deleteMemberDAO = new DeleteMemberDAO();

        Gson outGson = new Gson();
        Map<String,String> resultMap = new HashMap<>();
        System.out.println(deleteMemberDTO.getPassword());
        System.out.println(deleteMemberDTO.getUsername());

        if (deleteMemberDAO.deleteMember(deleteMemberDTO)) {
            System.out.println("success");
            resultMap.put("message", "ok");
//            ScriptWriter.alertAndNext(resp, "회원가입되었습니다.", "/index/index");
        } else {
            System.out.println("fail");
            resp.setStatus(400);
            resultMap.put("message", "fail");
//            ScriptWriter.alertAndBack(resp,"알 수 없는 오류가 발생되었습니다.");
            resp.setStatus(400);
        }

        String resultJson = outGson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);

    }
}
