package com.desk8432.project.dao.group;

import com.desk8432.project.dto.group.GroupDTO;
import com.desk8432.project.dto.group.InsertGroupDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class InsertGroupDAO {
    public boolean insertGroup(InsertGroupDTO insertgroupDTO) {
        int result = 0;

        SqlSession sqlsession = MybatisConnectionFactory.getSqlSession();
        result = sqlsession.insert("insertGroup", insertgroupDTO);

        return result > 0;
    }

}
