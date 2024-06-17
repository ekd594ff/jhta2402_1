package com.desk8432.project.dao.member;

import com.desk8432.project.dto.member.DeleteMemberDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class DeleteMemberDAO {
    public boolean deleteMember(DeleteMemberDTO deleteMemberDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.update("deleteMember", deleteMemberDTO);
        System.out.println("result = " + result);
        sqlSession.close();
        return result > 0;
    }
}
