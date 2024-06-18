package com.desk8432.project.controller.schedule;

import com.desk8432.project.dao.schedule.UpdateScheduleDAO;
import com.desk8432.project.dto.member.UpdateIntroductionDTO;
import com.desk8432.project.dto.schedule.UpdateScheduleDTO;
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

@WebServlet("/schedule/update")
public class UpdateSchedule extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dispatcher dispatcher = new Dispatcher();
        String jsonString = dispatcher.getBody(req);
        System.out.println("jsonString = " + jsonString);
        Gson gson = new Gson();
        UpdateScheduleDTO updateScheduleDTO = gson.fromJson(jsonString, UpdateScheduleDTO.class);
        System.out.println("updateScheduleDTO = " + updateScheduleDTO.toString());

        UpdateScheduleDAO updateScheduleDAO = new UpdateScheduleDAO();
        updateScheduleDTO.setGroupname(updateScheduleDAO.getGroupNameDTO(updateScheduleDTO)); //groupname을 가진 DTO
        UpdateScheduleDAO updateScheduleDAO1 = new UpdateScheduleDAO();

        Gson outGson = new Gson();
        resp.setContentType("application/json");
        if (updateScheduleDAO1.updateSchedule(updateScheduleDTO)) {
            System.out.println("success");
            resp.getWriter().print(outGson.toJson(updateScheduleDTO));
            resp.setStatus(200);
//            ScriptWriter.alertAndNext(resp,"스케줄 변경이 완료되었습니다","/index/index");
        } else {
            System.out.println("fail");
            Map<String,String> resultMap = new HashMap<>();
            resultMap.put("message","fail");
            String resultJson = outGson.toJson(resultMap);
            resp.setContentType("application/json; charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.println(resultJson);
            resp.setStatus(400);
//            ScriptWriter.alertAndBack(resp,"스케줄 변경이 실패되었습니다");
        }
    }
}
