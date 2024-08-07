package com.desk8432.project.dao.group;

import com.desk8432.project.dto.group.FollowRequestDTO;
import com.desk8432.project.dto.group.IsFollowDTO;
import com.desk8432.project.dto.group.SearchGroupDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class FollowGroupDAO {

    public List<SearchGroupDTO> getFollowGroup(String username) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        List<SearchGroupDTO> groupDTOList = sqlSession.selectList("getFollowGroup", username);
        sqlSession.close();
        return groupDTOList;
    }

    public SearchGroupDTO getGroupInfo(FollowRequestDTO followRequestDTO) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        SearchGroupDTO searchGroupDTO = sqlSession.selectOne("getGroupInformation", followRequestDTO);
        sqlSession.close();
        return searchGroupDTO;
    }
//    public List<SearchGroupDTO> getMyGroups(String username){
//        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
//        List<SearchGroupDTO> groupDTOList = sqlSession.selectList("getMyGroups", username);
//        sqlSession.close();
//        return  groupDTOList;
//    }

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

    public boolean isFollowGroup(IsFollowDTO isFollowDTO) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        int result = sqlSession.selectOne("isFollowGroup", isFollowDTO);
        sqlSession.close();
        return result > 0;
    }
}
