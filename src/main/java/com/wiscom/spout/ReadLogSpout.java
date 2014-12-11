package com.wiscom.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.wiscom.bean.RawSyslogBean;
import com.wiscom.db.LoadAllLogDao;

import java.util.List;
import java.util.Map;

/**
 * Created by cjf on 2014/12/10.
 */
public class ReadLogSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;

    LoadAllLogDao loadAllLogDao;
    private List<RawSyslogBean> list;
    private int count=0;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("deviceid", "servrity", "entity"));
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        collector = spoutOutputCollector;
        loadAllLogDao = new LoadAllLogDao();
        list = loadAllLogDao.getAllRawSyslog();
    }

    @Override
    public void nextTuple() {
        if(count<list.size()){
            RawSyslogBean bean = list.get(count);
            collector.emit(new Values(bean.getDeviceID(), bean.getServrityDesc(), bean));
        }
        count++;
    }
}
