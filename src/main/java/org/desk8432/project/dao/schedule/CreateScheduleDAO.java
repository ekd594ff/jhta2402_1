package org.desk8432.project.dao.schedule;

import org.desk8432.project.dto.schedule.ScheduleDTO;
import org.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class CreateScheduleDAO {
    public boolean createSchedule(ScheduleDTO scheduleDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.insert("createSchedule", scheduleDTO);
        sqlSession.close();
        return result > 0;
    }

    public boolean createGroupSchedule(ScheduleDTO scheduleDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.insert("createGroupSchedule", scheduleDTO);
        sqlSession.close();
        return result > 0;
    }
}
