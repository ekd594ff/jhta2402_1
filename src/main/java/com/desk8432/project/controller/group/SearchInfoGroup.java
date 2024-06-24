package com.desk8432.project.controller.group;

import com.desk8432.project.dao.group.FollowGroupDAO;
import com.desk8432.project.dto.group.FollowRequestDTO;
import com.desk8432.project.dto.group.SearchGroupDTO;
import com.desk8432.project.util.CookieManager;
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


@WebServlet("/group/searchInfo")
public class SearchInfoGroup extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FollowRequestDTO followRequestDTO
                = FollowRequestDTO.builder()
                .groupID(Long.parseLong(req.getParameter("groupID")))
                .username(CookieManager.readCookie(req, "username"))
                .build();

        FollowGroupDAO getGroupInfoDAO = new FollowGroupDAO();
        SearchGroupDTO searchGroupDTO = getGroupInfoDAO.getGroupInfo(followRequestDTO);

        Map<String, SearchGroupDTO> resultMap = new HashMap<>();
        resultMap.put("groupInfo", searchGroupDTO);
        Gson gson = new Gson();
        String resultJson = gson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }
}
