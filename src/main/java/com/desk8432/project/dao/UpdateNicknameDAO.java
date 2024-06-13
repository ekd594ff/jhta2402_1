package com.desk8432.project.dao;

import com.desk8432.project.dto.DuplicateDTO;
import com.desk8432.project.dto.UpdateIntroductionDTO;
import com.desk8432.project.dto.UpdateNicknameDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import com.desk8432.project.util.Regex;
import org.apache.ibatis.session.SqlSession;

public class UpdateNicknameDAO {
    public boolean updateNickname(UpdateNicknameDTO updateNicknameDTO) {
        int result = 0;
        if (isNicknameOK(updateNicknameDTO)) {
            SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
            result = sqlSession.update("updateNickname", updateNicknameDTO);
            sqlSession.close();
        } else {
            return false;
        }
        return  result > 0;
    }
    private boolean isNicknameOK(UpdateNicknameDTO updateNicknameDTO) {
        DuplicateDAO duplicateDAO = new DuplicateDAO();
        DuplicateDTO duplicateDTO =
                DuplicateDTO.builder()
                        .nickname(updateNicknameDTO.getNickname())
                        .build();
        Regex regex = new Regex();

        boolean duplicateNickname = duplicateDAO.duplicateNickname(duplicateDTO);
        boolean regexNickname = regex.regexNickname(updateNicknameDTO.getNickname());

        return !duplicateNickname && regexNickname;
    }
}
