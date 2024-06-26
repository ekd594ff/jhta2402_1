package com.desk8432.project.controller.group;

import com.desk8432.project.dao.group.SearchGroupDAO;
import com.desk8432.project.dto.group.SearchGroupDTO;
import com.desk8432.project.dto.group.SearchGroupRequestDTO;
import com.desk8432.project.dto.group.SearchGroupResponseDTO;
import com.desk8432.project.util.CookieManager;
import com.desk8432.project.util.Dispatcher;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/group/search")
public class SearchGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/groupSearch.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Dispatcher dispatcher = new Dispatcher();
//        String jsonSTR = dispatcher.getBody(req);
//        System.out.println("jsonSTR = " + jsonSTR);

//        String searchQuery = req.getParameter("searchQuery");
//        resp.getWriter().println("Search Query == " + searchQuery);

        resp.setContentType("application/json; charset=utf-8");
        Gson gson = new Gson();
        SearchGroupRequestDTO searchGroupRequestDTO = gson.fromJson(req.getReader(), SearchGroupRequestDTO.class);
        SearchGroupDAO searchGroupDAO = new SearchGroupDAO();

        searchGroupRequestDTO.setUsername(CookieManager.readCookie(req,"username"));
        System.out.println("searchGroupRequestDTO = " + searchGroupRequestDTO);

        List<SearchGroupDTO> resultList = searchGroupDAO.getSearchGroupList(searchGroupRequestDTO);

        SearchGroupResponseDTO searchGroupResponseDTO =
                SearchGroupResponseDTO.builder()
                        .total(searchGroupDAO.getSearchTotal(searchGroupRequestDTO))
                        .result(resultList)
                        .build();

        if (searchGroupResponseDTO.getResult() != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            System.out.println(gson.toJson(searchGroupResponseDTO));
            resp.getWriter().write(gson.toJson(searchGroupResponseDTO));
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
