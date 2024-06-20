package com.desk8432.project.controller.group;

import com.desk8432.project.dao.group.FollowGroupDAO;
import com.desk8432.project.dto.group.FollowRequestDTO;
import com.desk8432.project.util.CookieManager;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/group/follow")
public class FollowGroup extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset=utf-8");
        Gson gson = new Gson();

        String username = CookieManager.readCookie(req, "username");
        FollowRequestDTO followRequestDTO = gson.fromJson(req.getReader(), FollowRequestDTO.class);
        followRequestDTO.setUsername(username);

        FollowGroupDAO followGroupDAO = new FollowGroupDAO();
        boolean result = followGroupDAO.addFollowGroup(followRequestDTO);

        if (result) {
            resp.setStatus(HttpServletResponse.SC_OK);
            Map<String, Long> resultMap = new HashMap<>();
            resultMap.put("id", followRequestDTO.getGroupID());
            resp.getWriter().write(gson.toJson(resultMap));
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        Gson gson = new Gson();

        String username = CookieManager.readCookie(req, "username");
        Long groupID = Long.parseLong(req.getParameter("groupID"));

        FollowGroupDAO followGroupDAO = new FollowGroupDAO();
        boolean result = followGroupDAO.deleteFollowGroup(
                FollowRequestDTO.builder()
                        .username(username)
                        .groupID(groupID)
                        .build());

        if (result) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
