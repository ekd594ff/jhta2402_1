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
}
