package com.desk8432.project.dao.group;

import com.desk8432.project.dto.group.GroupDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ListGroupDAO {
    public List<GroupDTO> getGroupList(String username) {
        List<GroupDTO> groupDTOList = null;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        groupDTOList = sqlSession.selectList("getGroupList", username);
        sqlSession.close();
        return groupDTOList;
    }

    public GroupDTO getGroupById(long groupId) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        GroupDTO groupDTO = sqlSession.selectOne("getGroupById", groupId);
        sqlSession.close();
        return groupDTO;
    }
}
