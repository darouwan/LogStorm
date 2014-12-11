package com.wiscom.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
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
    private static String sql="SELECT * FROM log_raw_syslog";

    LoadAllLogDao loadAllLogDao = new LoadAllLogDao();
    private List<RawSyslogBean> list=loadAllLogDao.getAllRawSyslog();
    private int count=0;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("DEVICEID","SOURCE","ENTITY"));
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        collector = spoutOutputCollector;

    }

    @Override
    public void nextTuple() {
        if(count<list.size()){
            RawSyslogBean bean = list.get(count);
            collector.emit(new Values(bean.getDeviceID(),bean.getSource(),bean.getContent()));
        }
        count++;
    }
}
