package org.desk8432.project.dao.schedule;

import org.desk8432.project.dto.schedule.ScheduleDTO;
import org.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class DeleteScheduleDAO {

    public boolean deleteSchedule(ScheduleDTO scheduleDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.delete("deleteSchedule", scheduleDTO);
        sqlSession.close();
        return result > 0;
    }
}
