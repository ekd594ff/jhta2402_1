package com.desk8432.project.controller.schedule;

import com.desk8432.project.dao.group.ListGroupDAO;
import com.desk8432.project.dao.schedule.ListScheduleDAO;
import com.desk8432.project.dto.group.GroupDTO;
import com.desk8432.project.dto.schedule.ScheduleDTO;
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
import java.util.List;
import java.util.Map;

@WebServlet("/schedule/list")
public class ListSchedule extends HttpServlet {
  
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/schedule.jsp").forward(req,resp);
    }
  
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = CookieManager.readCookie(req, "username");
        List<ScheduleDTO> scheduleDTOList = null;
        List<GroupDTO> groupDTOList = null;

        System.out.println("username = " + username);

        ListScheduleDAO listScheduleDAO = new ListScheduleDAO();
        scheduleDTOList = listScheduleDAO.getScheduleList(username);

        ListGroupDAO listGroupDAO = new ListGroupDAO();
        groupDTOList = listGroupDAO.getGroupList(username);

        //Map<String, Object>
        for (GroupDTO groupDTO : groupDTOList) {
            if (groupDTO.getImageUrl() == null) {
                groupDTO.setImageUrl("");
            }
        }

        Gson outGson = new Gson();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("username", username);
        resultMap.put("groups",groupDTOList);
        resultMap.put("events",scheduleDTOList);

        String resultJson = outGson.toJson(resultMap);

        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }
}
