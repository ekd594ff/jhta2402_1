package org.desk8432.project.dao.member;

import org.desk8432.project.dto.member.DuplicateDTO;
import org.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class DuplicateDAO {
    public boolean duplicateUsername(DuplicateDTO duplicateDTO) { // 중복이면 true
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.selectOne("duplicateUsername", duplicateDTO);
        System.out.println("username result = " + result);
        sqlSession.close();
        return result > 0;
    }
    public boolean duplicateNickname(DuplicateDTO duplicateDTO) { //중복이면 ture
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.selectOne("duplicateNickname", duplicateDTO);
        System.out.println("nickname result = " + result);
        sqlSession.close();
        return result > 0;
    }

    public boolean duplicateEmail(DuplicateDTO duplicateDTO) { //중복이면 true
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.selectOne("duplicateEmail",duplicateDTO);
        System.out.println("email result = " + result);
        sqlSession.close();
        return result > 0;
    }

    public boolean duplicateAll(DuplicateDTO duplicateDTO) { //모두 중복이지 않으면 true

        return !duplicateUsername(duplicateDTO) &&
                !duplicateNickname(duplicateDTO) &&
                !duplicateEmail(duplicateDTO);
    }
}
