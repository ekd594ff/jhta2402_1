package com.desk8432.project.dao.group;

import com.desk8432.project.dto.group.GroupDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class UpdateGroupDAO {
    public boolean updateGroupName(GroupDTO groupDTO) {
        int groupNameResult = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        groupNameResult =  sqlSession.update("updateGroupName", groupDTO);
        System.out.println("update group result == " + groupNameResult);
        sqlSession.close();
        return groupNameResult > 0;
    }

    public boolean updateContent(GroupDTO groupDTO) {
        int contentResult = 0;
        System.out.println("contentResult == " + contentResult);
        System.out.println("groupDTO == " + groupDTO);
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        contentResult =  sqlSession.update("updateContent", groupDTO);
        System.out.println("update content result == " + contentResult);
        sqlSession.close();
        return contentResult > 0;
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
