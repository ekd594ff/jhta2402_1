package org.desk8432.project.dao.group;

import org.desk8432.project.dto.group.InsertGroupDTO;
import org.desk8432.project.mybatis.MybatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;

public class InsertGroupDAO {
    public boolean insertGroup(InsertGroupDTO insertgroupDTO) {
        int result = 0 ;

        if (insertgroupDTO != null) {
            SqlSession sqlsession = MybatisConnectionFactory.getSqlSession();
            result = sqlsession.insert("insertGroup", insertgroupDTO);
            sqlsession.close();

            return result > 0;
        } else {
            return false;
        }

    }


}
