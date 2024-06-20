package com.desk8432.project.dao.group;

import com.desk8432.project.dto.group.FollowRequestDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class FollowGroupDAO {
    public boolean addFollowGroup(FollowRequestDTO followRequestDTO) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        int result = sqlSession.insert("insertFollowGroup", followRequestDTO);
        sqlSession.close();
        return result > 0;
    }

    public boolean deleteFollowGroup(FollowRequestDTO followRequestDTO) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        int result = sqlSession.delete("deleteFollowGroup", followRequestDTO);
        sqlSession.close();
        return result > 0;
    }
}
