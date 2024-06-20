package com.desk8432.project.dao.group;

import com.desk8432.project.dto.group.SearchGroupDTO;
import com.desk8432.project.dto.group.SearchGroupRequestDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SearchGroupDAO {

    public List<SearchGroupDTO> getSearchGroupList(SearchGroupRequestDTO searchGroupRequestDTO) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        List<SearchGroupDTO> searchGroupList = sqlSession.selectList("getSearchGroupList", searchGroupRequestDTO);
        sqlSession.close();
        return searchGroupList;
    }

    public int getSearchTotal(SearchGroupRequestDTO searchGroupRequestDTO) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        int searchTotal = sqlSession.selectOne("getSearchTotal", searchGroupRequestDTO);
        sqlSession.close();
        return searchTotal;
    }
}
