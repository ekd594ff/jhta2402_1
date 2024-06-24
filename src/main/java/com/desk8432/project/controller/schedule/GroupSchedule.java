package com.desk8432.project.controller.schedule;

import com.desk8432.project.dao.schedule.ListScheduleDAO;
import com.desk8432.project.dto.schedule.ScheduleDTO;
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

@WebServlet("/schedule/group")
public class GroupSchedule extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ListScheduleDAO groupScheduleDAO = new ListScheduleDAO();
        List<ScheduleDTO> groupScheduleDTOList
                = groupScheduleDAO.getGroupScheduleList(Long.parseLong(req.getParameter("groupID")));
        Map<String,List<ScheduleDTO>> resultMap = new HashMap<>();
        resultMap.put("events", groupScheduleDTOList);
        Gson gson = new Gson();
        String resultJson = gson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }
}
