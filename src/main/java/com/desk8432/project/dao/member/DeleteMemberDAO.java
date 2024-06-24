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

    public void deleteMemberGroup(String username) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        int result = sqlSession.delete("deleteMemberGroup", username);
        System.out.println(result + " Group row deleted");
        sqlSession.close();
    }

    public void deleteMemberSchedule(String username) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        int result = sqlSession.delete("deleteMemberSchedule", username);
        System.out.println(result + " Schedule row deleted");
        sqlSession.close();
    }

    public void deleteMemberFollow(String username) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        int result = sqlSession.delete("deleteMemberFollow", username);
        System.out.println(result + " Follow row deleted");
        sqlSession.close();
    }

    public void deleteMemberFile(String username) {
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        int result = sqlSession.delete("deleteMemberFile", username);
        System.out.println(result + " File row deleted");
        sqlSession.close();
    }
}
