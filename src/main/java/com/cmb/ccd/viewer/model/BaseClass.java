package com.cmb.ccd.viewer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LM on 2016/9/11.
 */
public abstract class BaseClass implements Comparable<BaseClass> {
    private String packageName;
    private String className;
    private List<String> context;
    private List<PublishPoint> publishPoints;
    public Pattern publishPattern = Pattern.compile("([a-z]|[A-Z])+Event");

    public BaseClass(List<String> context, String fileName) throws Exception {
        this.context = context;
        this.className = fileName.replace(".java", "");
        this.packageName = context.stream().filter(line -> line.startsWith("package"))
                .findFirst().get().replace("package ", "").replace(";", "");
        this.publishPoints = getPublishPoints(context);
    }

    private List<PublishPoint> getPublishPoints(List<String> context) {
        List<PublishPoint> points = new ArrayList<>();
        boolean isPointFind = false;
        for (int i = 0; i < context.size(); i++) {
            String line = context.get(i);
            if (line.contains(".publish(")) {
                isPointFind = true;
            }
            if (isPointFind) {
                Matcher matcher = publishPattern.matcher(line.replaceAll(".*publish",""));
                if (matcher.find()) {
                    points.add(new PublishPoint(matcher.group()));
                    isPointFind = false;
                }
            }
        }
        return points;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }


    public List<String> getContext() {
        return context;
    }

    public List<PublishPoint> getPublishPoints() {
        return publishPoints;
    }

    @Override
    public String toString() {
        String pointString = (getPublishPoints()==null || getPublishPoints().isEmpty()) ? "" :
                (",[pulishPoints=" +  (getPublishPoints().stream().map(x -> x.getEventName()).reduce((x, y) -> x + "," + y).get()) + "]");
        return "["+getPackageName() + "." + getClassName() + "]"+ pointString;
    }

    @Override
    public int compareTo(BaseClass clazz) {
        return this.toString().compareTo(clazz.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean isValidate() {
        return getPackageName() != null && getClassName() != null;
    }
}
