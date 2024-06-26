package com.desk8432.project.dao.group;

import com.desk8432.project.dto.group.GroupDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class UpdateGroupDAO {
    public boolean updateGroupNameContent(GroupDTO groupDTO) {
        int groupNameResult = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        groupNameResult =  sqlSession.update("updateGroupNameContent", groupDTO);
        System.out.println("update group result == " + groupNameResult);
        sqlSession.close();
        return groupNameResult > 0;
    }

    public boolean updateImage(GroupDTO groupDTO) {
        int imageResult = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        imageResult =  sqlSession.update("updateImage", groupDTO);
        System.out.println("update image result == " + imageResult);
        sqlSession.close();
        return imageResult > 0;
    }
}
