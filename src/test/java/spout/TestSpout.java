package spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

/**
 * Created by cjf on 2014/12/10.
 */
public class TestSpout extends BaseRichSpout {

    static final String[] words=new String[] {"apes","do","not","kill","human","apple"};
    Random random = new Random();
    SpoutOutputCollector spoutOutputCollector;
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word"));
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.spoutOutputCollector=spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        Utils.sleep(1000);
        int index = random.nextInt(words.length);
        this.spoutOutputCollector.emit(new Values(words[index]));
    }
}
