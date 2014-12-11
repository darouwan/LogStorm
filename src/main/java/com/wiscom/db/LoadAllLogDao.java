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
        String sql = "SELECT  DEVICEID,FACILITY,SERVRITY,CONTENT,TS,SERVRITY_DESC FROM log_raw_syslog";
        try {
            statement=conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                RawSyslogBean rawSyslogBean = new RawSyslogBean();
                rawSyslogBean.setDeviceID(resultSet.getString(1));
                rawSyslogBean.setFacility(resultSet.getString(2));
                rawSyslogBean.setServrity(resultSet.getString(3));
                rawSyslogBean.setContent(resultSet.getString(4));
                rawSyslogBean.setTs(resultSet.getDate(5));
                rawSyslogBean.setServrityDesc(resultSet.getString(6));
                list.add(rawSyslogBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }



}
