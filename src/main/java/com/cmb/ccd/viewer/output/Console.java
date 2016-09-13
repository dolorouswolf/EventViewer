package com.cmb.ccd.viewer.output;

import com.cmb.ccd.viewer.model.Event;
import com.cmb.ccd.viewer.model.Handler;
import com.cmb.ccd.viewer.model.Service;
import com.cmb.ccd.viewer.reader.ModelFactory;

import java.util.List;

/**
 * Created by LM on 2016/9/11.
 */
public class Console implements Display{

    private static String root = "F:\\Projects\\viewerFile";

    @Override
    public void display() {
        ModelFactory modelFactory = ModelFactory.init(root);
        List<Event> eventList = modelFactory.getEventList();
        for (Event event : eventList) {
            System.out.println(event.toString());
        }

        List<Handler> handlerList = modelFactory.getHandlerList();
        for (Handler handler : handlerList) {
            System.out.println(handler.toString());
        }

        List<Service> serviceList = modelFactory.getServiceList();
        for (Service service : serviceList) {
            System.out.println(service.toString());
        }
    }

    public static void main(String[] args) {
        Display display = new Console();
        display.display();
    }
}
