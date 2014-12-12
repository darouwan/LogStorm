package com.wiscom.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import com.wiscom.bolt.SplitBolt;
import com.wiscom.spout.ReadLogSpout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cjf on 2014/12/10.
 */
public class LogAnalyzeTopology {
    public static void main(String[] args){
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("read",new ReadLogSpout());
        builder.setBolt("split", new SplitBolt(), 2).fieldsGrouping("read", new Fields("deviceid", "servrity"));
        Map conf =new HashMap();
        conf.put(Config.TOPOLOGY_DEBUG, false);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("analyzeLog",conf,builder.createTopology());

        Utils.sleep(5000);
        cluster.shutdown();
    }
}
