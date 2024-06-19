package com.desk8432.project.controller.schedule;

import com.desk8432.project.dao.schedule.CreateScheduleDAO;
import com.desk8432.project.dto.schedule.ScheduleDTO;
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

@WebServlet("/schedule/create")
public class createSchedule extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher dispatcher = new Dispatcher();
        String jsonString = dispatcher.getBody(req);
        Gson gson = new Gson();
        ScheduleDTO scheduleDTO = gson.fromJson(jsonString, ScheduleDTO.class);

        String username = CookieManager.readCookie(req, "username");
        scheduleDTO.setEditor(username);

        System.out.println("scheduleDTO = " + scheduleDTO.toString());

        CreateScheduleDAO createScheduleDAO = new CreateScheduleDAO();
        boolean result;
        if (scheduleDTO.getGroupID() == 0) {
            result = createScheduleDAO.createSchedule(scheduleDTO);
        } else {
            result = createScheduleDAO.createGroupSchedule(scheduleDTO);
        }

        Gson outGson = new Gson();
        Map<String,String> resultMap = new HashMap<>();
        if (result) {
            System.out.println("add success");
            resultMap.put("message", "ok");
        } else {
            resp.setStatus(400);
            resultMap.put("message", "fail");
        }

        String resultJson = outGson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }
}
