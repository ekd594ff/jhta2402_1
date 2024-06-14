package com.desk8432.project.dao;

import com.desk8432.project.dto.UpdateImageUrlDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class UpdateImageUrlDAO {
    public boolean updateImageUrl(UpdateImageUrlDTO updateImageUrlDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.update("updateImageUrl", updateImageUrlDTO);
        sqlSession.close();
        return  result > 0;
    }
    //사진 url 설정
}
