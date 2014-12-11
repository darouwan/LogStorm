package com.wiscom.bean;

import java.util.Date;

/**
 * Created by cjf on 2014/12/10.
 */
public class RawSyslogBean {
    private String deviceID;
    private String logType;
    private String eventNO;
    private String source;
    private String facility;
    private String servrity;
    private String content;
    private Date ts;
    private String servrityDesc;
    private Date logDatetime;
    private int syslogID;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getEventNO() {
        return eventNO;
    }

    public void setEventNO(String eventNO) {
        this.eventNO = eventNO;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getServrity() {
        return servrity;
    }

    public void setServrity(String servrity) {
        this.servrity = servrity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public String getServrityDesc() {
        return servrityDesc;
    }

    public void setServrityDesc(String servrityDesc) {
        this.servrityDesc = servrityDesc;
    }

    public Date getLogDatetime() {
        return logDatetime;
    }

    public void setLogDatetime(Date logDatetime) {
        this.logDatetime = logDatetime;
    }

    public int getSyslogID() {
        return syslogID;
    }

    public void setSyslogID(int syslogID) {
        this.syslogID = syslogID;
    }
}
