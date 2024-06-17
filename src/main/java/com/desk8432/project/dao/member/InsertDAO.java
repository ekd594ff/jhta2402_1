package com.desk8432.project.dao.member;

import com.desk8432.project.dto.member.DuplicateDTO;
import com.desk8432.project.dto.member.InsertDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import com.desk8432.project.util.Regex;
import org.apache.ibatis.session.SqlSession;

public class InsertDAO {
    public boolean insertMember(InsertDTO insertdto) {
        int result = 0 ;

        if (useData(insertdto)) {
            SqlSession sqlsession = MybatisConnectionFactory.getSqlSession();
            result = sqlsession.insert("insertMember", insertdto);
            sqlsession.close();
        } else {
            return false;
        }
        return result > 0;
    }

    public boolean updateMember(InsertDTO insertDTO) {
        int result = 0 ;
        if (useData(insertDTO)) {
            SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
            result = sqlSession.update("updateMember", insertDTO);
            sqlSession.close();
        } else {
            return false;
        }
        return result > 0;
    }

    private boolean useData(InsertDTO insertdto) {
        String email = insertdto.getEmail();
        String username = insertdto.getUsername();
        String password = insertdto.getPassword();
        String nickname = insertdto.getNickname();

        Regex regex = new Regex();

        DuplicateDAO duplicateDAO = new DuplicateDAO();
        DuplicateDTO duplicateDTO =
                DuplicateDTO.builder()
                        .username(username)
                        .nickname(nickname)
                        .email(email)
                        .build();

        return duplicateDAO.duplicateAll(duplicateDTO)&&
                regex.regexAll(username, password, email, nickname);
    }
}
