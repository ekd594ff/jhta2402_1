package com.desk8432.project.controller.schedule;

import com.desk8432.project.dao.schedule.UpdateScheduleDAO;
import com.desk8432.project.dto.schedule.ScheduleDTO;
import com.desk8432.project.util.CookieManager;
import com.desk8432.project.util.Dispatcher;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
        ScheduleDTO scheduleDTO = gson.fromJson(jsonString, ScheduleDTO.class);
        System.out.println("updateScheduleDTO = " + scheduleDTO.toString());

        UpdateScheduleDAO updateScheduleDAO01 = new UpdateScheduleDAO();
        String username = CookieManager.readCookie(req, "username");
        String editor = updateScheduleDAO01.getEditorName(scheduleDTO.getId());



        if (username.equals(editor)) { //글 주인과 같다면 true
            UpdateScheduleDAO updateScheduleDAO = new UpdateScheduleDAO();
            scheduleDTO.setGroupname(updateScheduleDAO.getGroupNameDTO(scheduleDTO)); //groupname을 가진 DTO
            UpdateScheduleDAO updateScheduleDAO1 = new UpdateScheduleDAO();
            Gson outGson = new Gson();
            if (updateScheduleDAO1.updateSchedule(scheduleDTO)) { //update 성공한다면
                System.out.println("success");
                resp.getWriter().print(outGson.toJson(scheduleDTO));
                resp.setStatus(200);
//            ScriptWriter.alertAndNext(resp,"스케줄 변경이 완료되었습니다","/index/index");
            } else {        //update실패
                System.out.println("fail");
                resp.setContentType("application/json");
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("message", "fail");
                String resultJson = outGson.toJson(resultMap);
                resp.setContentType("application/json; charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.println(resultJson);
                resp.setStatus(400);
//            ScriptWriter.alertAndBack(resp,"스케줄 변경이 실패되었습니다");
            }
        }
    }
}
