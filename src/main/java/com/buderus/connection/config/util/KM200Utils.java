/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.eclipse.smarthome.core.thing.Channel
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.buderus.connection.config.util;

import com.buderus.connection.config.KM200Device;
import org.eclipse.smarthome.core.thing.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class KM200Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(KM200Utils.class);

    public static String translatesNameToPath(String name) {
        return name.replace("#", "/");
    }

    public static String translatesPathToName(String path) {
        return path.replace("/", "#");
    }

    public static String checkParameterReplacement(Channel channel, KM200Device device) {
        String currentService;
        String service = KM200Utils.translatesNameToPath((String)channel.getProperties().get("root"));
        if (service.contains("__current__") && device.containsService(currentService = KM200Utils.translatesNameToPath((String)channel.getProperties().get("activeSwitchProgram"))).booleanValue() && "stringValue".equals(device.getServiceObject(currentService).getServiceType())) {
            String val = (String)device.getServiceObject(currentService).getValue();
            service = service.replace("__current__", val);
            return service;
        }
        return service;
    }

    public static Map<String, String> getChannelConfigurationStrings(Channel channel) {
        HashMap<String, String> paraNames = new HashMap<String, String>();
        if (channel.getConfiguration().containsKey("on")) {
            paraNames.put("on", channel.getConfiguration().get("on").toString());
            LOGGER.debug("Added ON: {}", channel.getConfiguration().get("on"));
        }
        if (channel.getConfiguration().containsKey("off")) {
            paraNames.put("off", channel.getConfiguration().get("off").toString());
            LOGGER.debug("Added OFF: {}", channel.getConfiguration().get("off"));
        }
        return paraNames;
    }
}

