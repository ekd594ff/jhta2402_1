package com.desk8432.project.controller.group;

import com.desk8432.project.util.ScriptWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/group/update")
public class UpdateGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            ScriptWriter.alertAndNext(resp, "그룹 아이디가 필요합니다", "/");
            return;
        }

        req.setAttribute("id", Integer.parseInt(id));
        req.getRequestDispatcher("/WEB-INF/groupUpdateForm.jsp").forward(req, resp);
    }
}
