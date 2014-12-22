package com.wiscom.topology;

import backtype.storm.tuple.Fields;
import com.wiscom.spout.ReadLogSpout;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
import storm.trident.testing.MemoryMapState;
import storm.trident.testing.Split;

/**
 * Created by cjf on 2014/12/22.
 */
public class LogTridentTopology {
    public static void main(String[] args) {
        TridentTopology topology = new TridentTopology();
        TridentState wordCounts =
                topology.newStream("spout1", new ReadLogSpout())
                        .each(new Fields("sentence"), new Split(), new Fields("word"))
                        .groupBy(new Fields("word"))
                        .persistentAggregate(new MemoryMapState.Factory(), new Count(), new Fields("count"))
                        .parallelismHint(6);

    }
}
