package org.desk8432.project.controller.schedule;

import org.desk8432.project.dao.schedule.DeleteScheduleDAO;
import org.desk8432.project.dto.schedule.ScheduleDTO;
import org.desk8432.project.util.CookieManager;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/schedule/delete")
public class DeleteSchedule extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset=utf-8");
        Gson gson = new Gson();

        String id = req.getParameter("id");
        String username = CookieManager.readCookie(req, "username");
        Map<String, Object> resultMap = new HashMap<>();

        boolean result = false;

        if (id != null) {
            DeleteScheduleDAO dao = new DeleteScheduleDAO();

            ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                    .id(Integer.parseInt(id))
                    .editor(username)
                    .build();

            result = dao.deleteSchedule(scheduleDTO);
            System.out.println(result);
        }

        if (result) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resultMap.put("status", "success");
            resultMap.put("id", id);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultMap.put("status", "error");
            resultMap.put("id", id);
        }

        resp.getWriter().println(gson.toJson(resultMap));
    }
}
