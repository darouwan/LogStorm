package topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import bolt.TestBolt;
import spout.TestSpout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cjf on 2014/12/10.
 */
public class TestTopology {
    public static void main(String[] args){
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout",new TestSpout());
        builder.setBolt("bold",new TestBolt(),2).fieldsGrouping("spout",new Fields("word"));



        Map conf = new HashMap();
        conf.put(Config.TOPOLOGY_WORKERS, 4);
        conf.put(Config.TOPOLOGY_DEBUG, true);

        LocalCluster cluster = new LocalCluster();


        cluster.submitTopology("mytopology", conf, builder.createTopology());
        Utils.sleep(10000);
        cluster.shutdown();
    }
}
