package com.desk8432.project.dao.schedule;

import com.desk8432.project.dto.schedule.UpdateScheduleDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class UpdateScheduleDAO {
    public UpdateScheduleDTO getGroupName(UpdateScheduleDTO updateScheduleDTO) {
        UpdateScheduleDTO updateScheduleDTO1;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        updateScheduleDTO1 = sqlSession.selectOne("getGroupName", updateScheduleDTO);
        sqlSession.close();
        return updateScheduleDTO1;
    }
}
