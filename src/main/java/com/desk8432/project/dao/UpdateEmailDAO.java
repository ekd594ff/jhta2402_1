package com.desk8432.project.dao;

import com.desk8432.project.dto.DuplicateDTO;
import com.desk8432.project.dto.UpdateEmailDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import com.desk8432.project.util.Dispatcher;
import com.desk8432.project.util.Regex;
import org.apache.ibatis.session.SqlSession;

public class UpdateEmailDAO {
    public boolean updateEmail(UpdateEmailDTO updateEmailDTO) {
        int result = 0;
        if (isEmailOK(updateEmailDTO)) {
            SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
            result = sqlSession.update("updateEmail", updateEmailDTO);
            sqlSession.close();
        } else {
            return false;
        }
        return  result > 0;
    }

    private boolean isEmailOK(UpdateEmailDTO updateEmailDTO) {
        Regex regex = new Regex();
        DuplicateDAO duplicateDAO = new DuplicateDAO();

        DuplicateDTO duplicateDTO =
                DuplicateDTO.builder()
                        .email(updateEmailDTO.getEmail())
                        .build();

        boolean duplicateEmail = duplicateDAO.duplicateEmail(duplicateDTO);
        boolean regexEmail = regex.regexEmail(updateEmailDTO.getEmail());

        return !duplicateEmail && regexEmail;
    }
}
