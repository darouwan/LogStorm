package com.wiscom.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import storm.trident.TridentTopology;
import storm.trident.operation.CombinerAggregator;
import storm.trident.operation.Filter;
import storm.trident.operation.TridentOperationContext;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.testing.Split;
import storm.trident.tuple.TridentTuple;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cjf on 2014/12/22.
 */
public class LogTridentTopology {
    public static void aggregateTest() {
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("sentence"), 3,
                new Values("the cow jumped over the moon"),
                new Values("the man went to the store and bought some candy"),
                new Values("four score and seven years ago"),
                new Values("how many apples can you eat"));

        spout.setCycle(true);
        TridentTopology topology = new TridentTopology();

                topology.newStream("spout1", spout)
                        .each(new Fields("sentence"), new Split(), new Fields("word")).groupBy(new Fields("word")).
                        aggregate(new One(), new Fields("one")).each(new Fields("one"), new PrintFilter());
        LocalCluster localCluster = new LocalCluster();
        Map conf = new HashMap();
        conf.put(Config.TOPOLOGY_DEBUG, false);
        localCluster.submitTopology("test", conf, topology.build());
        // topology.newDRPCStream("reach").stateQuery(wordCounts,new Fields("word"),new MapGet(),new Fields("count")).aggregate(new One(), new Fields("one"));
    }


    public static void main(String[] args) {
        TridentTopology topology = new TridentTopology();
//        TridentState wordCounts =
//                topology.newStream("spout1", new TridentTestSpout())
//                        .each(new Fields("sentence"), new Split(), new Fields("word"))
//                        D
//        topology.newDRPCStream("words")
//                .each(new Fields("args"), new Split(), new Fields("word"))
//                .groupBy(new Fields("word"))
//                .stateQuery(wordCounts, new Fields("word"), new MapGet(), new Fields("count"))
//                .each(new Fields("count"), new FilterNull())
//                .aggregate(new Fields("count"), new Sum(), new Fields("sum"));

        aggregateTest();
    }

    private static class One implements CombinerAggregator<Integer> {

        @Override
        public Integer init(TridentTuple tuple) {
            return 1;
        }

        @Override
        public Integer combine(Integer val1, Integer val2) {
            return 1;
        }

        @Override
        public Integer zero() {
            return 1;
        }
    }

    private static class PrintFilter implements Filter {
        @Override
        public boolean isKeep(TridentTuple tuple) {
            System.out.println(tuple.getInteger(0));
            return true;
        }

        @Override
        public void prepare(Map conf, TridentOperationContext context) {

        }

        @Override
        public void cleanup() {

        }
    }
}
