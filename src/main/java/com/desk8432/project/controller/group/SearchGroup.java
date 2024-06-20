package com.desk8432.project.controller.group;

import com.desk8432.project.dao.group.SearchGroupDAO;
import com.desk8432.project.dto.group.SearchGroupRequestDTO;
import com.desk8432.project.dto.group.SearchGroupResponseDTO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/group/search")
public class SearchGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/groupSearch.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset=utf-8");
        Gson gson = new Gson();

        SearchGroupRequestDTO searchGroupRequestDTO = gson.fromJson(req.getReader(), SearchGroupRequestDTO.class);
        SearchGroupDAO searchGroupDAO = new SearchGroupDAO();

        SearchGroupResponseDTO searchGroupResponseDTO =
                SearchGroupResponseDTO.builder()
                        .total(searchGroupDAO.getSearchTotal(searchGroupRequestDTO))
                        .result(searchGroupDAO.getSearchGroupList(searchGroupRequestDTO))
                        .build();

        if (searchGroupResponseDTO.getResult() != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(searchGroupResponseDTO));
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
