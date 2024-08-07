package com.desk8432.project.controller.member;

import com.desk8432.project.dao.member.LoginDAO;
import com.desk8432.project.dto.member.LoginDTO;
import com.desk8432.project.dto.member.LoginMemberDTO;
import com.desk8432.project.util.CookieManager;
import com.desk8432.project.util.Dispatcher;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.jdbc.Null;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 처음 로그인 화면을 띄워주고, ID,PW 입력 시 ID,PW 가져옵니다.
@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher dispatcher = new Dispatcher();
        String jsonString = dispatcher.getBody(req);

        Gson gson = new Gson();
        LoginDTO loginDTO = gson.fromJson(jsonString, LoginDTO.class);

        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        LoginDAO loginDAO = new LoginDAO();
        // Bcrypt로 암호화된 String  암호화된비밀번호.salt
        // 만들어진 salt를 가져오기
        String hashPW = loginDAO.getPassword(username);

        Map<String,String> resultMap = new HashMap<>();

        if (hashPW != null) {
            LoginMemberDTO loginMemberDTO = loginDAO.loginMember(
                    LoginDTO.builder()
                            .username(username)
                            .password(hashPW)
                            .build());

            boolean checkLogin = ((loginMemberDTO != null) && BCrypt.checkpw(password, hashPW));

            System.out.println("loginMemberDTO = " + loginMemberDTO);
            System.out.println("loginMemberDTO != null" + (loginMemberDTO != null));
            System.out.println("checkLogin = " + checkLogin);

            if (checkLogin) {
                // myPageDTO를 쿠키 저장 / 세션에 저장
                HttpSession session = req.getSession();
                session.setAttribute("member", loginMemberDTO);

                CookieManager.createCookie(resp, "username", username, 60 * 60 * 24 * 7);

                resp.setStatus(200);
                resultMap.put("message", "ok");
            } else {
                CookieManager.deleteCookie(resp, "username");
                resp.setStatus(400);
                resultMap.put("message", "fail");
            }
        } else {
            CookieManager.deleteCookie(resp, "username");
            resp.setStatus(400);
            resultMap.put("message", "fail");
        }

        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(resultMap));

    }
}
