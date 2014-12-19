package com.wiscom.kafka;


import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by cjf on 2014/12/17.
 */
public class TestMessageScheme implements Scheme {
    @Override
    public List<Object> deserialize(byte[] bytes) {
        try {
            String msg = new String(bytes, "UTF-8");
            return new Values(msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //TODO: what happend if returns null?
        return null;
    }

    @Override
    public Fields getOutputFields() {

        return new Fields("msg");
    }
}
