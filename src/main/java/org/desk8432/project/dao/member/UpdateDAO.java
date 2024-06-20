package org.desk8432.project.dao.member;

import org.desk8432.project.dto.member.*;
import org.desk8432.project.dto.member.*;
import org.desk8432.project.mybatis.MybatisConnectionFactory;
import org.desk8432.project.util.Regex;
import org.apache.ibatis.session.SqlSession;

public class UpdateDAO {
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

    public boolean updateImageUrl(UpdateImageUrlDTO updateImageUrlDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.update("updateImageUrl", updateImageUrlDTO);
        System.out.println("result = " + result);
        sqlSession.close();
        return  result > 0;
    }

    public boolean isFile(UpdateImageUrlDTO updateImageUrlDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.selectOne("isFile", updateImageUrlDTO);
        sqlSession.close();
        return result > 0;
    }

    //사진 url 설정
    public boolean updateFile(UpdateImageUrlDTO updateImageUrlDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.update("updateFile", updateImageUrlDTO);
        sqlSession.close();
        return result > 0;
    }
    public boolean insertFile(UpdateImageUrlDTO updateImageUrlDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.insert("insertFIle", updateImageUrlDTO);
        sqlSession.close();
        return result > 0;
    }

    public boolean updateIntroduction(UpdateIntroductionDTO updateIntroductionDTO) {
        int result = 0;
        SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
        result = sqlSession.update("updateIntroduction", updateIntroductionDTO);
        sqlSession.close();
        return  result > 0;
    }
    //글자수 제약

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

    public boolean updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        int result = 0;
        if (isPasswrodOk(updatePasswordDTO)) {
            SqlSession sqlSession = MybatisConnectionFactory.getSqlSession();
            result = sqlSession.update("updatePassword", updatePasswordDTO);
            sqlSession.close();
        } else {
            return false;
        }
        return  result > 0;
    }
    private boolean isPasswrodOk(UpdatePasswordDTO updatePasswordDTO) {
        Regex regex = new Regex();
        return regex.regexPassword(updatePasswordDTO.getPassword());
    }
}
