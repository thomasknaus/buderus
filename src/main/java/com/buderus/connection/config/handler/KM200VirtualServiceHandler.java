/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.buderus.connection.config.handler;

import com.buderus.connection.config.KM200Device;
import com.buderus.connection.config.KM200ServiceObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KM200VirtualServiceHandler {
    private final Logger logger = LoggerFactory.getLogger(KM200VirtualServiceHandler.class);
    private final KM200Device remoteDevice;

    public KM200VirtualServiceHandler(KM200Device remoteDevice) {
        this.remoteDevice = remoteDevice;
    }

    public void initVirtualObjects() {
        KM200ServiceObject newObject = null;
        try {
            for (KM200ServiceObject object : this.remoteDevice.virtualList) {
                String type;
                this.logger.debug("Full Servicename: {}", (Object)object.getFullServiceName());
                String id = object.getFullServiceName();
                switch (type = object.getServiceType()) {
                    case "switchProgram": {
                        KM200SwitchProgramServiceHandler sPService = (KM200SwitchProgramServiceHandler)object.getValueParameter();
                        if (!sPService.determineSwitchNames(this.remoteDevice)) {
                            this.logger.info("No references for switch service: {}, this is not supported", (Object)object.getFullServiceName());
                            break;
                        }
                        JsonObject nodeRoot = object.getJSONData();
                        sPService.updateSwitches(nodeRoot, this.remoteDevice);
                        newObject = new KM200ServiceObject(String.valueOf(id) + "/weekday", type, 1, 1, 0, 1, id, object);
                        object.serviceTreeMap.put("weekday", newObject);
                        newObject = new KM200ServiceObject(String.valueOf(id) + "/nbrCycles", type, 1, 0, 0, 1, id, object);
                        object.serviceTreeMap.put("nbrCycles", newObject);
                        newObject = new KM200ServiceObject(String.valueOf(id) + "/cycle", type, 1, 1, 0, 1, id, object);
                        object.serviceTreeMap.put("cycle", newObject);
                        this.logger.debug("On: {}  Of: {}", (Object)(String.valueOf(id) + "/" + sPService.getPositiveSwitch()), (Object)(String.valueOf(id) + "/" + sPService.getNegativeSwitch()));
                        newObject = new KM200ServiceObject(String.valueOf(id) + "/" + sPService.getPositiveSwitch(), type, 1, object.getWriteable(), object.getRecordable(), 1, id, object);
                        object.serviceTreeMap.put(sPService.getPositiveSwitch(), newObject);
                        newObject = new KM200ServiceObject(String.valueOf(id) + "/" + sPService.getNegativeSwitch(), type, 1, object.getWriteable(), object.getRecordable(), 1, id, object);
                        object.serviceTreeMap.put(sPService.getNegativeSwitch(), newObject);
                        break;
                    }
                    case "errorList": {
                        newObject = new KM200ServiceObject(String.valueOf(id) + "/nbrErrors", type, 1, 0, 0, 1, id, object);
                        object.serviceTreeMap.put("nbrErrors", newObject);
                        newObject = new KM200ServiceObject(String.valueOf(id) + "/error", type, 1, 1, 0, 1, id, object);
                        object.serviceTreeMap.put("error", newObject);
                        newObject = new KM200ServiceObject(String.valueOf(id) + "/errorString", type, 1, 0, 0, 1, id, object);
                        object.serviceTreeMap.put("errorString", newObject);
                    }
                }
            }
        }
        catch (JsonParseException e) {
            this.logger.error("Parsingexception in JSON: {}", (Object)e.getMessage());
        }
    }
}

