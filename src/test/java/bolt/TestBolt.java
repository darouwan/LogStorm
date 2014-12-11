package bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import java.util.HashMap;

/**
 * Created by cjf on 2014/12/10.
 */
public class TestBolt extends BaseBasicBolt {
    HashMap<String,Integer> countMap = new HashMap<String, Integer>();

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String word = tuple.getString(0);
        Integer count = countMap.get(word);
        if(count==null){
            countMap.put(word,1);
        }else{
            count++;
            countMap.put(word,count);
        }
        System.out.println(word+":"+count);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

}
