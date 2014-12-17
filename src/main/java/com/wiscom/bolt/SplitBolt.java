package com.wiscom.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import com.wiscom.bean.RawSyslogBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cjf on 2014/12/10.
 */
public class SplitBolt extends BaseBasicBolt {
    String pattern = "主墙";

    /**
     * Process the input tuple and optionally emit new tuples based on the input tuple.
     * <p/>
     * All acking is managed for you. Throw a FailedException if you want to fail the tuple.
     *
     * @param input
     * @param collector
     */
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        Pattern r = Pattern.compile(pattern);
        //String content = ((RawSyslogBean)input.getValue(2)).getContent();
        String content = input.getString(2);
        System.out.println("====================");
        System.out.println(content);
        Matcher m = r.matcher(content);
        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            // System.out.println("Found value: " + m.group(1) );
            // System.out.println("Found value: " + m.group(2) );
        } else {
            System.out.println("NO MATCH");
        }
    }

    /**
     * Declare the output schema for all the streams of this topology.
     *
     * @param declarer this is used to declare output stream ids, output fields, and whether or not each output stream is a direct stream
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
