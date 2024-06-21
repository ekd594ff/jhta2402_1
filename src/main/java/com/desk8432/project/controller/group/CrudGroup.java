package com.desk8432.project.controller.group;

import com.desk8432.project.dao.group.DeleteGroupDAO;
import com.desk8432.project.dao.group.InsertGroupDAO;
import com.desk8432.project.dao.member.LoginDAO;
import com.desk8432.project.dto.group.DeleteGroupDTO;
import com.desk8432.project.dto.group.FollowRequestDTO;
import com.desk8432.project.dto.group.InsertGroupDTO;
import com.desk8432.project.dto.member.DeleteMemberDTO;
import com.desk8432.project.util.CookieManager;
import com.desk8432.project.util.Dispatcher;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/group/crud")
public class CrudGroup extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/groupform.jsp")
                .forward(request,response);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher dispatcher = new Dispatcher();
        String jsonString = dispatcher.getBody(req);

        System.out.println("jsonString = " + jsonString);


        Gson gson = new Gson();
        InsertGroupDTO insertgroupDTO = gson.fromJson(jsonString, InsertGroupDTO.class);

        InsertGroupDAO insertGroupDAO = new InsertGroupDAO();

        System.out.println(insertgroupDTO.toString());
        Gson outGson = new Gson();
        Map<String, String> resultMap = new HashMap<>();
        boolean result = insertGroupDAO.insertGroup(insertgroupDTO);
        if (result) {
            System.out.println("success");
            resultMap.put("message", "ok");
            // ScriptWriter.alertAndNext(resp, "회원가입되었습니다.", "/index/index");
        } else {
            System.out.println("fail");
            resp.setStatus(400);
            resultMap.put("message", "fail");
            // ScriptWriter.alertAndBack(resp,"알 수 없는 오류가 발생되었습니다.");
        }

        String resultJson = outGson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        // request 에서 값 받아오기
        String username = CookieManager.readCookie(req, "username");
        Long groupID = Long.parseLong(req.getParameter("id"));
        System.out.println("groupID == " + groupID);
        System.out.println("username == " + username);

        // DAO 에서 DB 삭제 처리
        DeleteGroupDAO deleteGroupDAO = new DeleteGroupDAO();
        boolean result = deleteGroupDAO.deleteGroup(
                DeleteGroupDTO.builder()
                        .groupID(groupID)
                        .creator(username)
                        .build());

        if (result) {
            resp.setStatus(HttpServletResponse.SC_OK);
            System.out.println("성공~!");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println("실패 ㅠㅠ");

        }
    }
}
