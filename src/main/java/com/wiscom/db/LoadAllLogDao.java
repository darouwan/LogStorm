package com.wiscom.db;

import com.wiscom.bean.RawSyslogBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjf on 2014/12/10.
 */
public class LoadAllLogDao {

    PreparedStatement statement;
    ResultSet resultSet;

    public List<RawSyslogBean> getAllRawSyslog(){
        List<RawSyslogBean> list = new ArrayList<RawSyslogBean>();
        Connection conn = DatabaseConnection.getConnection();
        String sql="SELECT  DEVICEID,SOURCE,CONTENT,LOG_DATETIME FROM log_raw_syslog";
        try {
            statement=conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                RawSyslogBean rawSyslogBean = new RawSyslogBean();
                rawSyslogBean.setDeviceID(resultSet.getString(1));
                rawSyslogBean.setSource(resultSet.getString(2));
                rawSyslogBean.setContent(resultSet.getString(3));
                rawSyslogBean.setLogDatetime(resultSet.getDate(4));
                list.add(rawSyslogBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }



}
