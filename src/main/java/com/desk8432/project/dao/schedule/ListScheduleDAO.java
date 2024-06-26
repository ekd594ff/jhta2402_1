package com.desk8432.project.dao.schedule;

import com.desk8432.project.dto.group.SearchGroupDTO;
import com.desk8432.project.dto.schedule.ScheduleDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ListScheduleDAO {
    public List<ScheduleDTO> getScheduleList(String username) {
        List<ScheduleDTO> scheduleDTOList = null;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        scheduleDTOList = sqlSession.selectList("getScheduleList", username);
        sqlSession.close();
        return scheduleDTOList;
    }

    public List<ScheduleDTO> getGroupScheduleList(Long groupID) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        List<ScheduleDTO> searchGroupDTOList = sqlSession.selectList("getGroupScheduleList", groupID);
        sqlSession.close();
        return searchGroupDTOList;
    }
}
