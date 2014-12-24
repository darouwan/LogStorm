package com.wiscom.topology;

import backtype.storm.tuple.Fields;
import com.wiscom.spout.ReadLogSpout;
import com.wiscom.spout.TridentTestSpout;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.FilterNull;
import storm.trident.operation.builtin.MapGet;
import storm.trident.operation.builtin.Sum;
import storm.trident.testing.MemoryMapState;
import storm.trident.testing.Split;

/**
 * Created by cjf on 2014/12/22.
 */
public class LogTridentTopology {
    public static void main(String[] args) {
        TridentTopology topology = new TridentTopology();
        TridentState wordCounts =
                topology.newStream("spout1", new TridentTestSpout())
                        .each(new Fields("sentence"), new Split(), new Fields("word"))
                        .groupBy(new Fields("word"))
                        .persistentAggregate(new MemoryMapState.Factory(), new Count(), new Fields("count"))
                        .parallelismHint(6);

        topology.newDRPCStream("words")
                .each(new Fields("args"), new Split(), new Fields("word"))
                .groupBy(new Fields("word"))
                .stateQuery(wordCounts, new Fields("word"), new MapGet(), new Fields("count"))
                .each(new Fields("count"), new FilterNull())
                .aggregate(new Fields("count"), new Sum(), new Fields("sum"));

        TridentState urlToTweeters =
                topology.newStaticState(getUrlToTweetersState());
        TridentState tweetersToFollowers =
                topology.newStaticState(getTweeterToFollowersState());

        topology.newDRPCStream("reach")
                .stateQuery(urlToTweeters, new Fields("args"), new MapGet(), new Fields("tweeters"))
                .each(new Fields("tweeters"), new ExpandList(), new Fields("tweeter"))
                .shuffle()
                .stateQuery(tweetersToFollowers, new Fields("tweeter"), new MapGet(), new Fields("followers"))
                .parallelismHint(200)
                .each(new Fields("followers"), new ExpandList(), new Fields("follower"))
                .groupBy(new Fields("follower"))
                .aggregate(new One(), new Fields("one"))
                .parallelismHint(20)
                .aggregate(new Count(), new Fields("reach"));
    }
}
