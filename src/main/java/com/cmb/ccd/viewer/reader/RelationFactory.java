package com.cmb.ccd.viewer.reader;

import com.cmb.ccd.viewer.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by LM on 2016/9/11.
 */
public class RelationFactory {

//    private Map<Aggregation, List<Event>> aggregationEventsMap;
//    private Map<Event, List<Handler>> eventHandlersMap;
//    private Map<Handler, List<HandleWay>> handlerHandleWaysMap;
//    private static RelationFactory instance = new RelationFactory();
//
//    public static RelationFactory init(String root) {
//        instance.aggregationEventsMap = instance.readAggregationEventRelation(root);
//        instance.eventHandlersMap = instance.readEventHandlerRelation(root);
//
//        return instance;
//    }
//
//    public Map<Event, List<Handler>> readEventHandlerRelation(String root) {
//
//        return new HashMap<>();
//    }
//    public Map<Aggregation, List<Event>> readAggregationEventRelation(String root) {
//        File file = new File(root);
//        Map<Aggregation, List<Event>> map = new HashMap<>();
//        read(map, file);
//        return map;
//    }
//
//
//    private void read(Map<Aggregation, List<Event>> map, File dir) {
//        if (!dir.isDirectory())
//            return;
//        for (File file : dir.listFiles()) {
//            if (file.isDirectory()) {
//                read(map, file);
//            } else if (file.isFile()) {
//                Map<Aggregation, List<Event>> cMap = transformToRelation(file);
//                if (cMap == null)
//                    continue;
//                for (Aggregation aggregation : cMap.keySet()) {
//                    if (map.containsKey(aggregation)) {
//                        map.get(aggregation).addAll(cMap.get(aggregation));
//                    } else {
//                        map.put(aggregation, cMap.get(aggregation));
//                    }
//                }
//            }
//        }
//    }
//
//    private Map<Aggregation, List<Event>> transformToRelation(File file) {
//        String fileName = file.getName();
//        if (fileName.endsWith(handlerSuffix)) {
//            return transformToHandler(file);
//        } else if (fileName.endsWith(eventSuffix)) {
//            return transformToEvent(file);
//        }
//        return null;
//    }
//
//    private Handler transformToHandler(File file) {
//        try {
//            List<String> context = readContext(file.getAbsolutePath());
//            Handler handler = new Handler(context, file.getName());
//            return handler;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    private Event transformToEvent(File file) {
//        try {
//            List<String> context = readContext(file.getAbsolutePath());
//            Event event = new Event(context, file.getName());
//            return event;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public List<String> readContext(String path) throws IOException {
//
//        FileInputStream inputStream = null;
//        Scanner sc = null;
//        List<String> context = new ArrayList<>();
//        try {
//            inputStream = new FileInputStream(path);
//            sc = new Scanner(inputStream, "UTF-8");
//            boolean isComment = false;
//            while (sc.hasNextLine()) {
//                String line = sc.nextLine();
//                if ("".equals(line.trim()))
//                    continue;
//                if (line.trim().startsWith("//"))
//                    continue;
//                if (line.trim().startsWith("/*")) {
//                    isComment = true;
//                    continue;
//                }
//                if (line.trim().endsWith("*/")) {
//                    isComment = false;
//                    continue;
//                }
//                if (isComment)
//                    continue;
//                context.add(line.trim().replaceAll("\\s+", " "));
//            }
//            // note that Scanner suppresses exceptions
//            if (sc.ioException() != null) {
//                throw sc.ioException();
//            }
//        } finally {
//            if (inputStream != null) {
//                inputStream.close();
//            }
//            if (sc != null) {
//                sc.close();
//            }
//        }
//        return context;
//    }
//
//
//
//    public Map<Event, Handler> getEventHandlerMap() {
//        return eventHandlerMap;
//    }
//
//    public Map<Handler, HandleWay> getHandlerHandleWayMap() {
//        return handlerHandleWayMap;
//    }
}
