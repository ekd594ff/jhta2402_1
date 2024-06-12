package com.desk8432.project.dao;

import com.desk8432.project.dto.DuplicateDTO;
import com.desk8432.project.dto.InsertDTO;
import com.desk8432.project.dto.MemberDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import com.desk8432.project.util.Regex;
import org.apache.ibatis.session.SqlSession;

public class InsertDAO {
    public boolean insertMember(InsertDTO insertdto) {
        int result = 0 ;

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

        if (duplicateDAO.duplicateAll(duplicateDTO)&&
                regex.regexAll(username, password, email, nickname)) {
            SqlSession sqlsession = MybatisConnectionFactory.getSqlSession();
            result = sqlsession.insert("insertMember", insertdto);
            sqlsession.close();
        } else {
            return false;
        }
        return result > 0;
    }
}
