package com.desk8432.project.dao.schedule;

import com.desk8432.project.dto.schedule.UpdateScheduleDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class UpdateScheduleDAO {
    public String getGroupNameDTO(UpdateScheduleDTO updateScheduleDTO) {
        String groupname = null;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        groupname = sqlSession.selectOne("getGroupName", updateScheduleDTO);
        sqlSession.close();
        return groupname;
    }

    public boolean updateSchedule(UpdateScheduleDTO updateScheduleDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.update("updateSchedule", updateScheduleDTO);
        sqlSession.close();
        return result > 0;
    }
}
