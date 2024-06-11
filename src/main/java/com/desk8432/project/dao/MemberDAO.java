package com.desk8432.project.dao;

import com.desk8432.project.dto.MemberDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class MemberDAO {
    public MemberDTO getMember() {
        MemberDTO memberDTO = null;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        memberDTO = sqlSession.selectOne("getMember", "test01");
        sqlSession.close();
        return memberDTO;
    }

    public boolean duplicateUsername(String testID) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.selectOne("duplicateUsername", testID);
        System.out.println("username result = " + result);
        sqlSession.close();
        return result > 0;
    }

    public boolean duplicateNickname(String testID) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.selectOne("duplicateNickname", testID);
        System.out.println("nickname result = " + result);
        sqlSession.close();
        return result > 0;
    }

    public boolean duplicateEmail(String testID) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.selectOne("duplicateEmail", testID);
        System.out.println("email result = " + result);
        sqlSession.close();
        return result > 0;
    }
}
