package com.desk8432.project.controller.schedule;

import com.desk8432.project.dao.group.FollowGroupDAO;
import com.desk8432.project.dao.group.InsertGroupDAO;
import com.desk8432.project.dao.group.ListGroupDAO;
import com.desk8432.project.dao.schedule.ListScheduleDAO;
import com.desk8432.project.dto.group.GroupDTO;
import com.desk8432.project.dto.group.InsertGroupDTO;
import com.desk8432.project.dto.group.IsFollowDTO;
import com.desk8432.project.dto.schedule.ScheduleDTO;
import com.desk8432.project.util.CookieManager;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/schedule/group")
public class GroupSchedule extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ListScheduleDAO groupScheduleDAO = new ListScheduleDAO();
        List<ScheduleDTO> groupScheduleDTOList
                = groupScheduleDAO.getGroupScheduleList(Long.parseLong(req.getParameter("groupID")));
        Map<String, List<ScheduleDTO>> resultMap = new HashMap<>();
        resultMap.put("events", groupScheduleDTOList);
        Gson gson = new Gson();
        String resultJson = gson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset=utf-8");
        Gson gson = new Gson();

        String username = CookieManager.readCookie(req, "username");

        System.out.println("username = " + username);

        HttpSession session = req.getSession();
        Long groupId = (Long) session.getAttribute("groupId");

        if (groupId == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        System.out.println("groupID : " + groupId);

        ListScheduleDAO listScheduleDAO = new ListScheduleDAO();
        List<ScheduleDTO> scheduleDTOList = listScheduleDAO.getGroupScheduleList(groupId);

        ListGroupDAO listGroupDAO = new ListGroupDAO();
        GroupDTO groupDTO = listGroupDAO.getGroupById(groupId);

        FollowGroupDAO followGroupDAO = new FollowGroupDAO();
        boolean isFollow = followGroupDAO.isFollowGroup(IsFollowDTO.builder()
                .groupID(groupId)
                .username(username)
                .build());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("username", username);
        resultMap.put("group", groupDTO);
        resultMap.put("isFollow", isFollow);
        resultMap.put("events", scheduleDTOList);

        String resultJson = gson.toJson(resultMap);
        resp.getWriter().println(resultJson);
    }
}
