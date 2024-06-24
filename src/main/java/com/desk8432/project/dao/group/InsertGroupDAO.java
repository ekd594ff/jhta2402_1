package com.desk8432.project.dao.group;

import com.desk8432.project.dto.group.InsertGroupDTO;
import com.desk8432.project.mybatis.MybatisConnectionFactory;
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

    public long getGroupId(InsertGroupDTO insertgroupDTO) {
        SqlSession sqlsession = MybatisConnectionFactory.getSqlSession();
        long groupId = sqlsession.selectOne("getGroupId", insertgroupDTO);
        sqlsession.close();
        return groupId;
    }

    public InsertGroupDTO getGroupInfo(long groupId) {
        SqlSession sqlsession = MybatisConnectionFactory.getSqlSession();
        InsertGroupDTO groupInfoDTO = sqlsession.selectOne("getGroupInfo", groupId);
        sqlsession.close();
        return groupInfoDTO;
    }

}
