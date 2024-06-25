package com.desk8432.project.controller.member;

import com.desk8432.project.dao.member.DeleteMemberDAO;
import com.desk8432.project.dao.member.LoginDAO;
import com.desk8432.project.dto.member.DeleteMemberDTO;
import com.desk8432.project.util.CookieManager;
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

        String username = CookieManager.readCookie(req, "username");
        deleteMemberDTO.setUsername(username);

        LoginDAO loginDAO = new LoginDAO();
        String hashPW = loginDAO.getPassword(username);
        // Bcrypt로 암호화된 String  암호화된비밀번호.salt
        // 만들어진 salt를 가져오기
        //json으로 받고 DTO에 입력
        String resultJson ="";
        if (hashPW != null) {
            Boolean checkLogin = BCrypt.checkpw(deleteMemberDTO.getPassword(), hashPW);

            if (checkLogin) {
                deleteMemberDTO.setPassword(hashPW);
                resultJson = deleteMember(resp, deleteMemberDTO);
            } else {
                CookieManager.deleteCookie(resp, "username");
                System.out.println("no user check false");
            }
        } else {
            CookieManager.deleteCookie(resp, "username");
            System.out.println("no user hash false");
        }

        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }

    private String deleteMember(HttpServletResponse resp, DeleteMemberDTO deleteMemberDTO) throws IOException {
        DeleteMemberDAO deleteMemberDAO = new DeleteMemberDAO();

        Gson outGson = new Gson();
        Map<String,String> resultMap = new HashMap<>();
        System.out.println(deleteMemberDTO.getPassword());
        System.out.println(deleteMemberDTO.getUsername());

        if (deleteMemberDAO.deleteMember(deleteMemberDTO)) {
            deleteMemberDAO.deleteMemberGroup(deleteMemberDTO.getUsername());
            deleteMemberDAO.deleteMemberSchedule(deleteMemberDTO.getUsername());
            deleteMemberDAO.deleteMemberFollow(deleteMemberDTO.getUsername());
            deleteMemberDAO.deleteMemberFile(deleteMemberDTO.getUsername());

            System.out.println("success");
            resultMap.put("message", "ok");
//            ScriptWriter.alertAndNext(resp, "회원가입되었습니다.", "/index/index");
        } else {
            System.out.println("fail");
            resp.setStatus(400);
            resultMap.put("message", "fail");
//            ScriptWriter.alertAndBack(resp,"알 수 없는 오류가 발생되었습니다.");
        }

        String resultJson = outGson.toJson(resultMap);
        return resultJson;
    }
}
