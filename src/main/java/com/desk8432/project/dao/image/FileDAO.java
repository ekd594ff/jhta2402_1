package com.desk8432.project.dao.image;

import com.desk8432.project.dto.image.InputImageUrlDTO;
import com.desk8432.project.dto.member.UpdateImageUrlDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class FileDAO {

    public boolean isFile(UpdateImageUrlDTO updateImageUrlDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.selectOne("isFile", updateImageUrlDTO);
        sqlSession.close();
        return result > 0;
    }

    public boolean isFileGroup(InputImageUrlDTO inputImageUrlDTO) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        int result = sqlSession.selectOne("isFileGroup", inputImageUrlDTO);
        sqlSession.close();
        return result > 0;
    }

    public boolean updateFileGroup(InputImageUrlDTO inputImageUrlDTO) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        int result = sqlSession.update("updateFileGroup", inputImageUrlDTO);
        sqlSession.close();
        return result > 0;
    }

    public boolean insertFileGroup(InputImageUrlDTO inputImageUrlDTO) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        int result = sqlSession.insert("insertFileGroup", inputImageUrlDTO);
        sqlSession.close();
        return result > 0;
    }
}
