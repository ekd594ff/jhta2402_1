package com.desk8432.project.dao;

import com.desk8432.project.dto.UpdateImageUrlDTO;
import com.desk8432.project.dto.UpdateIntroductionDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class UpdateIntroductionDAO {
    public boolean updateIntroduction(UpdateIntroductionDTO updateIntroductionDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.update("updateIntroduction", updateIntroductionDTO);
        sqlSession.close();
        return  result > 0;
    }
    //글자수 제약
}
