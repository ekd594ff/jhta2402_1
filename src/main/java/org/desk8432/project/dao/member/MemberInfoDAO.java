package org.desk8432.project.dao.member;

import org.desk8432.project.dto.member.MyPageDTO;
import org.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class MemberInfoDAO {
    public MyPageDTO getMemberInfo (String username) {
        // get info(available) by username value in cookie
        MyPageDTO memberInfo = null;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        memberInfo = sqlSession.selectOne("getMember", username);
        sqlSession.close();
        return memberInfo;
    }
}
