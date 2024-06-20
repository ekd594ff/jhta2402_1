package org.desk8432.project.dao.schedule;

import org.desk8432.project.dto.schedule.ScheduleDTO;
import org.desk8432.project.mybatis.MybatisConnectionFactory;
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
}
