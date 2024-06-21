package com.desk8432.project.controller.group;

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
            resp.sendRedirect("/");
            return;
        }

        req.setAttribute("id", Integer.parseInt(id));
        req.getRequestDispatcher("/WEB-INF/groupform.jsp").forward(req, resp);
    }
}
