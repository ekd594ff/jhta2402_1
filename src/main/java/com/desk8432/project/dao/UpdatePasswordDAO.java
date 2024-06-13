package com.desk8432.project.dao;

import com.desk8432.project.dto.UpdateNicknameDTO;
import com.desk8432.project.dto.UpdatePasswordDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import com.desk8432.project.util.Regex;
import org.apache.ibatis.session.SqlSession;

public class UpdatePasswordDAO {
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
