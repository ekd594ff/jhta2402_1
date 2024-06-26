package com.desk8432.project.controller.group;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/group/page")
public class GroupPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long groupId = Long.parseLong(req.getParameter("id"));
        req.setAttribute("id", groupId);

        HttpSession session = req.getSession();
        session.setAttribute("groupId", groupId);
        req.getRequestDispatcher("/WEB-INF/group.jsp").forward(req,resp);
    }
}
