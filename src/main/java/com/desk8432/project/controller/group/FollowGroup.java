package com.desk8432.project.controller.group;

import com.desk8432.project.dao.group.FollowGroupDAO;
import com.desk8432.project.dto.group.FollowRequestDTO;
import com.desk8432.project.dto.group.GroupViewResponseDTO;
import com.desk8432.project.dto.group.SearchGroupDTO;
import com.desk8432.project.util.CookieManager;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/group/follow")
public class FollowGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        Gson gson = new Gson();

        String username = CookieManager.readCookie(req, "username");

        FollowGroupDAO followGroupDAO = new FollowGroupDAO();
        List<SearchGroupDTO> searchGroupDTOList = followGroupDAO.getFollowGroup(username);

        if (searchGroupDTOList != null) {
            GroupViewResponseDTO groupViewResponseDTO = getGroupViewResponseDTO(searchGroupDTOList, username);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(groupViewResponseDTO));
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset=utf-8");
        Gson gson = new Gson();

        String username = CookieManager.readCookie(req, "username");
        FollowRequestDTO followRequestDTO = gson.fromJson(req.getReader(), FollowRequestDTO.class);
        followRequestDTO.setUsername(username);

        FollowGroupDAO followGroupDAO = new FollowGroupDAO();
        boolean result = followGroupDAO.addFollowGroup(followRequestDTO);

        if (result) {
            resp.setStatus(HttpServletResponse.SC_OK);
            Map<String, Long> resultMap = new HashMap<>();
            resultMap.put("id", followRequestDTO.getGroupID());
            resp.getWriter().write(gson.toJson(resultMap));
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        String username = CookieManager.readCookie(req, "username");
        Long groupID = Long.parseLong(req.getParameter("groupID"));

        FollowGroupDAO followGroupDAO = new FollowGroupDAO();
        boolean result = followGroupDAO.deleteFollowGroup(
                FollowRequestDTO.builder()
                        .username(username)
                        .groupID(groupID)
                        .build());

        if (result) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private GroupViewResponseDTO getGroupViewResponseDTO(List<SearchGroupDTO> searchGroupDTOList, String username) {
        List<SearchGroupDTO> myGroupList = new ArrayList<>();
        List<SearchGroupDTO> followList = new ArrayList<>();

        for (SearchGroupDTO searchGroupDTO : searchGroupDTOList) {
            if (searchGroupDTO.getCreator().equals(username)) {
                myGroupList.add(searchGroupDTO);
            } else {
                followList.add(searchGroupDTO);
            }
        }

        return GroupViewResponseDTO.builder()
                .myGroups(myGroupList)
                .followGroups(followList)
                .build();
    }
}
