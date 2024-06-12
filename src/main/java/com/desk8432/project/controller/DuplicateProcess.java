package com.desk8432.project.controller;

import com.desk8432.project.dao.DuplicateDAO;
import com.desk8432.project.dto.DuplicateDTO;
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

@WebServlet("/duplicate/*")
public class DuplicateProcess extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo().substring(1);
        boolean isDuplication = false;

        Dispatcher dispatcher = new Dispatcher();
        String jsonString = dispatcher.getBody(req);
        Gson inGson = new Gson();
        DuplicateDTO duplicateDTO = inGson.fromJson(jsonString, DuplicateDTO.class);

        DuplicateDAO duplicateDAO = new DuplicateDAO();

        switch (pathInfo) {
            case "username":
                String username = duplicateDTO.getUsername();
                isDuplication = duplicateDAO.duplicateUsername(duplicateDTO);
                break;
            case "email":
                String email = duplicateDTO.getEmail();
                isDuplication = duplicateDAO.duplicateEmail(duplicateDTO);
                break;
            case "nickname":
                String nickname = duplicateDTO.getNickname();
                isDuplication = duplicateDAO.duplicateNickname(duplicateDTO);
                break;
        }
        Gson outGson = new Gson();
        Map<String,Boolean> resultMap = new HashMap<>();
        resultMap.put("isDuplication", isDuplication);
        String resultJson = outGson.toJson(resultMap);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(resultJson);
    }
}
