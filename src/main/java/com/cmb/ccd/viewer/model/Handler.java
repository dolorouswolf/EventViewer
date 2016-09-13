package com.cmb.ccd.viewer.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by LM on 2016/9/11.
 */
public class Handler extends BaseClass{
    private Pattern aggregationPattern = Pattern.compile("([a-z]|[A-Z])+(Service|Dao|Repository)");

    private List<String> aggregationHandles;

    public Handler(List<String> context, String fileName) throws Exception{
        super(context, fileName);
        aggregationHandles = context.stream().filter(x->x.contains("@Autowired"))
                .map(x-> {
                    Matcher matcher = aggregationPattern.matcher(x.replace("@Autowired ",""));
                    if (matcher.find()){
                        return matcher.group();
                    } else {
                        return "";
                    }
                }).filter(x-> x != "").collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Handler:" + super.toString();
    }
    public boolean handledByAggregation() {
        return getAggregationHandles() != null && getAggregationHandles().size() > 0;
    }
    public boolean handledByPublish() {
        return getPublishPoints() != null && getPublishPoints().size() > 0;
    }

    public List<String> getAggregationHandles() {
        return aggregationHandles;
    }
}
