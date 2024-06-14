package com.desk8432.project.dao;

import com.desk8432.project.dto.LoginDTO;
import com.desk8432.project.dto.LoginMemberDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class LoginDAO {

    public LoginMemberDTO loginMember(LoginDTO loginDTO) {

        SqlSession sqlsession = MybatisConnectionFactory.getSqlSession();
        LoginMemberDTO resultDto = sqlsession.selectOne("loginMember", loginDTO);
        sqlsession.close();

        return resultDto;
    }

    public String getPassword(String username) {

        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        String hashPW = sqlSession.selectOne("getPassword", username);
        sqlSession.close();

        return hashPW;
    }

}
