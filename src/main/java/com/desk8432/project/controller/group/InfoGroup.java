package com.desk8432.project.controller.group;

import com.desk8432.project.dao.group.InsertGroupDAO;
import com.desk8432.project.dto.group.InsertGroupDTO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/group/info")
public class InfoGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset=utf-8");
        Gson gson = new Gson();

        String idString = req.getParameter("id");
        if (idString == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        long id = Long.parseLong(idString);

        InsertGroupDAO insertGroupDAO = new InsertGroupDAO();
        InsertGroupDTO insertGroupDTO = insertGroupDAO.getGroupInfo(id);

        System.out.println(insertGroupDTO.getCreator());
        resp.setStatus(HttpServletResponse.SC_OK);
        gson.toJson(insertGroupDTO, resp.getWriter());

    }
}
