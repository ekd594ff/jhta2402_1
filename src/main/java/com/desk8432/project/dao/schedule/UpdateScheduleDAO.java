package com.desk8432.project.dao.schedule;

import com.desk8432.project.dto.schedule.ScheduleDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class UpdateScheduleDAO {
    public String getGroupNameDTO(ScheduleDTO scheduleDTO) {
        String groupname = null;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        groupname = sqlSession.selectOne("getGroupName", scheduleDTO);
        sqlSession.close();
        return groupname;
    }

    public boolean updateSchedule(ScheduleDTO scheduleDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.update("updateSchedule", scheduleDTO);
        sqlSession.close();
        return result > 0;
    }
}
