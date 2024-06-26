package com.desk8432.project.dao.group;

import com.desk8432.project.dto.group.DeleteGroupDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class DeleteGroupDAO {
    public boolean deleteGroup(DeleteGroupDTO deleteGroupDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.delete("deleteGroup", deleteGroupDTO);
        System.out.println("result = " + result);
        sqlSession.close();
        return result > 0;
    }
}
