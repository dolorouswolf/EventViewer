package com.cmb.ccd.viewer.reader;

import com.cmb.ccd.viewer.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by LM on 2016/9/11.
 */
public class ModelFactory {
    private static final String handlerSuffix = "Handler.java";
    private static final String eventSuffix = "Event.java";
    private static final String serviceSuffix = "Service.java";
    private static final String oldServiceSuffix = "ServiceImp.java";

    private List<Event> eventList = new ArrayList<>();
    private List<Handler> handlerList = new ArrayList<>();
    private List<Service> serviceList = new ArrayList<>();

    private static ModelFactory instance = new ModelFactory();

    public static ModelFactory init(String root) {
        List<BaseClass> baseClassList = instance.readAll(root);
        instance.eventList = baseClassList.stream().filter(x -> x != null && x.isValidate() &&x instanceof Event)
                .map(x -> (Event) x)
                .collect(Collectors.toList());
        instance.handlerList = baseClassList.stream().filter(x -> x != null && x.isValidate() &&x instanceof Handler)
                .map(x -> (Handler) x)
                .collect(Collectors.toList());
        instance.serviceList = baseClassList.stream().filter(x -> x != null && x.isValidate() && x instanceof Service)
                .map(x -> (Service) x)
                .collect(Collectors.toList());
        return instance;
    }

    private List<BaseClass> readAll(String root) {
        List<BaseClass> classList = new ArrayList<>();
        File file = new File(root);
        read(classList, file);
        return classList;
    }

    private void read(List<BaseClass> classList, File dir) {
        if (!dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                read(classList, file);
            } else if (file.isFile()) {
                BaseClass clazz = transformToModel(file);
                if (clazz != null)
                    classList.add(clazz);
            }
        }
    }

    private BaseClass transformToModel(File file) {
        String fileName = file.getName();
        if (fileName.endsWith(handlerSuffix)) {
            return transformToHandler(file);
        } else if (fileName.endsWith(eventSuffix)) {
            return transformToEvent(file);
        } else if (fileName.endsWith(serviceSuffix) || fileName.endsWith(oldServiceSuffix)) {
            return transformToService(file);
        } else if (file.getPath().endsWith("model")) {
            return transformToAggregation(file);
        }
        return null;
    }

    private Handler transformToHandler(File file) {
        try {
            List<String> context = readContext(file.getAbsolutePath());
            Handler handler = new Handler(context, file.getName());
            return handler;
        } catch (Exception e) {
            return null;
        }
    }

    private Event transformToEvent(File file) {
        try {
            List<String> context = readContext(file.getAbsolutePath());
            Event event = new Event(context, file.getName());
            return event;
        } catch (Exception e) {
            return null;
        }
    }

    private Service transformToService(File file) {
        try {
            List<String> context = readContext(file.getAbsolutePath());
            Service service = new Service(context, file.getName());
            return service;
        } catch (Exception e) {
        }
        return null;
    }

    private Aggregation transformToAggregation(File file) {
        try {
            List<String> context = readContext(file.getAbsolutePath());
            Aggregation aggregation = new Aggregation(context, file.getName());
            return aggregation;
        } catch (Exception e) {
            return null;
        }
    }


    public List<String> readContext(String path) throws IOException {

        FileInputStream inputStream = null;
        Scanner sc = null;
        List<String> context = new ArrayList<>();
        try {
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream, "UTF-8");
            boolean isComment = false;
            String currentLine = "";
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if ("".equals(line.trim()))
                    continue;
                if (line.trim().startsWith("//"))
                    continue;
                if (line.trim().startsWith("/*")) {
                    isComment = true;
                    continue;
                }
                if (line.trim().endsWith("*/")) {
                    isComment = false;
                    continue;
                }
                if (isComment)
                    continue;
                line = line.trim().replaceAll("\\s+", " ");
                currentLine = currentLine == "" ? line : currentLine + " " + line;
                if (line.contains("{") || line.contains("}") || line.contains(";")) {
                    context.add(currentLine);
                    currentLine = "";
                }
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }
        return context;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public List<Handler> getHandlerList() {
        return handlerList;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }
}
