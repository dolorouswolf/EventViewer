package com.cmb.ccd.viewer.model;

import java.util.List;

/**
 * Created by LM on 2016/9/11.
 */
public class Aggregation extends BaseClass{


    public Aggregation(List<String> context, String fileName) throws Exception{
        super(context, fileName);
    }

    @Override
    public String toString() {
        return "A:"+super.toString();
    }
}
