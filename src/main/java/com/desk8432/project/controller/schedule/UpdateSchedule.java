package com.desk8432.project.controller.schedule;

import com.desk8432.project.dao.schedule.UpdateScheduleDAO;
import com.desk8432.project.dto.UpdateIntroductionDTO;
import com.desk8432.project.dto.schedule.UpdateScheduleDTO;
import com.desk8432.project.util.Dispatcher;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/schedule/update")
public class UpdateSchedule extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher dispatcher = new Dispatcher();
        String jsonString = dispatcher.getBody(req);

        Gson gson = new Gson();
        UpdateScheduleDTO updateScheduleDTO = gson.fromJson(jsonString, UpdateScheduleDTO.class);
        UpdateScheduleDAO updateScheduleDAO = new UpdateScheduleDAO();

        UpdateScheduleDTO updateScheduleDTO1 = updateScheduleDAO.getGroupName(updateScheduleDTO); //groupname을 가진 DTO


        Gson outGson = new Gson();
        resp.setContentType("application/json");
        resp.getWriter().print(outGson.toJson(updateScheduleDTO1));
        resp.setStatus(200);
    }
}
