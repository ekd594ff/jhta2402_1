package com.desk8432.project.controller.member;

import com.desk8432.project.dao.member.DeleteMemberDAO;
import com.desk8432.project.dao.member.LoginDAO;
import com.desk8432.project.dto.member.DeleteMemberDTO;
import com.desk8432.project.util.CookieManager;
import com.desk8432.project.util.Dispatcher;
import com.desk8432.project.util.ScriptWriter;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        String result = "";
        Gson outGson = new Gson();
        Map<String,String> resultMap = new HashMap<>();
        if (hashPW != null) {
            Boolean checkLogin = BCrypt.checkpw(deleteMemberDTO.getPassword(), hashPW);

            if (checkLogin) {
                deleteMemberDTO.setPassword(hashPW);
                if (deleteMember(resp, deleteMemberDTO)) {
                    CookieManager.deleteCookie(resp, "username");
                    HttpSession session = req.getSession();
                    session.setAttribute("member", null);
                    resultMap.put("success", "ok");
                    resultMap.put("message", "회원탈퇴가 완료되었습니다");
                } else {
                    resultMap.put("success", "fail");
                    resultMap.put("message", "알 수 없는 오류가 발생되었습니다.");
                }
            } else {
                System.out.println("no user check false");
                resultMap.put("success", "fail");
                resultMap.put("message", "비밀번호가 틀렸습니다.");
            }
        } else {
            CookieManager.deleteCookie(resp, "username");
            System.out.println("no user hash false");
            resultMap.put("success", "fail");
            resultMap.put("message", "가입된 유저의 정보가 없습니다.");
        }
        resultJson = outGson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }

    private boolean deleteMember(HttpServletResponse resp, DeleteMemberDTO deleteMemberDTO) throws IOException {
        DeleteMemberDAO deleteMemberDAO = new DeleteMemberDAO();

        System.out.println(deleteMemberDTO.getPassword());
        System.out.println(deleteMemberDTO.getUsername());

        if (deleteMemberDAO.deleteMember(deleteMemberDTO)) {
            deleteMemberDAO.deleteMemberGroup(deleteMemberDTO.getUsername());
            deleteMemberDAO.deleteMemberSchedule(deleteMemberDTO.getUsername());
            deleteMemberDAO.deleteMemberFollow(deleteMemberDTO.getUsername());
            deleteMemberDAO.deleteMemberFile(deleteMemberDTO.getUsername());

            System.out.println("success");
            return true;
//            ScriptWriter.alertAndNext(resp, "회원가입되었습니다.", "/index/index");
        } else {
            System.out.println("fail");
            resp.setStatus(400);
            return false;
//            ScriptWriter.alertAndBack(resp,"알 수 없는 오류가 발생되었습니다.");
        }
    }
}
