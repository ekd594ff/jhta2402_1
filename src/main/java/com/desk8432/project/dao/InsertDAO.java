package com.desk8432.project.dao;

import com.desk8432.project.dto.InsertDTO;
import com.desk8432.project.dto.MemberDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class InsertDAO {
    public boolean insertMember(InsertDTO Insertdto) {
        int result = 0 ;
        SqlSession sqlsession = MybatisConnectionFactory.getSqlSession();
        result = sqlsession.insert("insertMember",Insertdto);
        System.out.println("result = " + result);
        sqlsession.close();
        return result > 0;
    }
}
