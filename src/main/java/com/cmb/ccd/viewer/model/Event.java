package com.cmb.ccd.viewer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LM on 2016/9/11.
 */
public class Event extends BaseClass {
    private final String EXTERNAL_SUFFIX = "ExtEvent";
    private Pattern parentEventPattern = Pattern.compile("([a-z]|[A-Z])+Event");

    public enum EventType {
        INTERNAL, EXTERNAL
    }
    private String parentEventName;
    private EventType eventType;
    private boolean publishable;
    private String eventTopic;
    private List<Handler> handlers = new ArrayList<>();

    public Event(List<String> context, String fileName) throws Exception {
        super(context, fileName);
        eventType = super.getClassName().endsWith(EXTERNAL_SUFFIX) ? EventType.EXTERNAL : EventType.INTERNAL;
        publishable = context.stream().anyMatch(line -> line.contains("implements Publishable"));
        eventTopic = publishable ? context.stream().filter(line -> line.contains("return \""))
                .findFirst().get().replace("return \"", "").replace("\";", "") : "";
        Optional<String> optional = context.stream().filter(line -> line.contains("extends")).findFirst();
        if (optional.isPresent()) {
            String line = optional.get();
            Matcher matcher = parentEventPattern.matcher(line.substring(line.indexOf("extends") + 1));
            if (matcher.find())
                parentEventName = matcher.group();
        }
    }

    public EventType getEventType() {
        return eventType;
    }

    public boolean isPublishable() {
        return publishable;
    }

    public String getEventTopic() {
        return eventTopic;
    }

    public void addHandler(Handler handler) {
        handlers.add(handler);
    }

    public List<Handler> getHandlers() {
        return handlers;
    }

    public boolean hasParent() {
        return parentEventName != null && parentEventName != "";
    }

    public String getParentEventName() {
        return parentEventName;
    }

    @Override
    public boolean isValidate() {
        return super.isValidate() && getPackageName().endsWith(".event");
    }

    @Override
    public String toString() {
        String typeStr = ",[type=" + getEventType() + "]";
        String topicStr = isPublishable() ? ",[topic=" + getEventTopic() + "]" : "";
        String parentStr = hasParent() ? ",[parent="+getParentEventName()+"]" : "";
        return "Event:" + super.toString() + typeStr + topicStr + parentStr;
    }
}
