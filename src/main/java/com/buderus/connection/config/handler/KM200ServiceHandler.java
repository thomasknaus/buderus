/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.buderus.connection.config.handler;

import com.buderus.connection.config.KM200Device;
import com.buderus.connection.config.KM200ServiceObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class KM200ServiceHandler {
    private final Logger logger = LoggerFactory.getLogger(KM200ServiceHandler.class);
    private final String service;
    private final KM200ServiceObject parent;
    private final KM200Device remoteDevice;
    private KM200ServiceObject serviceObject;
    private JsonObject nodeRoot;

    public KM200ServiceHandler(String service, KM200ServiceObject parent, KM200Device remoteDevice) {
        this.service = service;
        this.parent = parent;
        this.remoteDevice = remoteDevice;
    }

    public void initObject() {
        if (this.remoteDevice.getBlacklistMap().contains(this.service)) {
            this.logger.debug("Blacklisted: {}", (Object)this.service);
            return;
        }
        this.nodeRoot = this.remoteDevice.getServiceNode(this.service);
        if (this.nodeRoot == null) {
            this.logger.debug("initDevice: nodeRoot == null for service: {}", (Object)this.service);
            return;
        }
        this.createServiceObject();
        this.determineServiceObject();
    }

    public void createServiceObject() {
        Integer val;
        String id = null;
        String type = null;
        Integer writeable = 0;
        Integer recordable = 0;
        Integer readable = 1;
        if (this.nodeRoot.toString().length() == 2) {
            readable = 0;
            id = this.service;
            type = "$$PROTECTED$$";
        } else {
            type = this.nodeRoot.get("type").getAsString();
            id = this.nodeRoot.get("id").getAsString();
        }
        if (this.nodeRoot.has("writeable")) {
            val = this.nodeRoot.get("writeable").getAsInt();
            this.logger.debug("writable: {}", (Object)val);
            writeable = val;
        }
        if (this.nodeRoot.has("recordable")) {
            val = this.nodeRoot.get("recordable").getAsInt();
            this.logger.debug("recordable: {}", (Object)val);
            recordable = val;
        }
        this.logger.debug("Typ: {}", (Object)type);
        this.serviceObject = new KM200ServiceObject(id, type, readable, writeable, recordable, 0, null, this.parent);
        this.serviceObject.setJSONData(this.nodeRoot);
    }

    public void determineServiceObject() {
        String id = null;
        Object valObject = null;
        JsonObject dataObject = this.serviceObject.getJSONData();
        switch (this.serviceObject.getServiceType()) {
            case "stringValue": {
                this.logger.debug("initDevice: type string value: {}", (Object)dataObject);
                valObject = new String(this.nodeRoot.get("value").getAsString());
                this.serviceObject.setValue(valObject);
                if (!this.nodeRoot.has("allowedValues")) break;
                ArrayList<Object> valParas = new ArrayList<Object>();
                JsonArray paras = this.nodeRoot.get("allowedValues").getAsJsonArray();
                for (int i = 0; i < paras.size(); ++i) {
                    String subJSON = paras.get(i).getAsString();
                    valParas.add(subJSON);
                }
                this.serviceObject.setValueParameter(valParas);
                break;
            }
            case "floatValue": {
                this.logger.debug("initDevice: type float value: {}", (Object)dataObject);
                valObject = this.nodeRoot.get("value");
                try {
                    valObject = this.nodeRoot.get("value").getAsBigDecimal();
                    this.serviceObject.setValue(valObject);
                }
                catch (NumberFormatException numberFormatException) {
                    this.logger.debug("float value is a string: {}", valObject);
                    Double tmpObj = Double.NaN;
                    this.serviceObject.setValue(tmpObj);
                }
                if (!this.nodeRoot.has("minValue") || !this.nodeRoot.has("maxValue")) break;
                ArrayList<Object> valParas = new ArrayList();
                valParas.add(this.nodeRoot.get("minValue").getAsBigDecimal());
                valParas.add(this.nodeRoot.get("maxValue").getAsBigDecimal());
                if (this.nodeRoot.has("unitOfMeasure")) {
                    valParas.add(this.nodeRoot.get("unitOfMeasure").getAsString());
                }
                this.serviceObject.setValueParameter(valParas);
                break;
            }
            case "switchProgram": {
                this.logger.debug("initDevice: type switchProgram {}", (Object)dataObject);
                KM200SwitchProgramServiceHandler sPService = new KM200SwitchProgramServiceHandler();
                sPService.setMaxNbOfSwitchPoints(this.nodeRoot.get("maxNbOfSwitchPoints").getAsInt());
                sPService.setMaxNbOfSwitchPointsPerDay(this.nodeRoot.get("maxNbOfSwitchPointsPerDay").getAsInt());
                sPService.setSwitchPointTimeRaster(this.nodeRoot.get("switchPointTimeRaster").getAsInt());
                JsonObject propObject = this.nodeRoot.get("setpointProperty").getAsJsonObject();
                sPService.setSetpointProperty(propObject.get("id").getAsString());
                this.serviceObject.setValueParameter(sPService);
                this.serviceObject.setJSONData(dataObject);
                this.remoteDevice.virtualList.add(this.serviceObject);
                break;
            }
            case "errorList": {
                this.logger.debug("initDevice: type errorList: {}", (Object)dataObject);
                KM200ErrorServiceHandler eService = new KM200ErrorServiceHandler();
                eService.updateErrors(this.nodeRoot);
                this.serviceObject.setValueParameter(eService);
                this.serviceObject.setJSONData(dataObject);
                this.remoteDevice.virtualList.add(this.serviceObject);
                break;
            }
            case "refEnum": {
                this.logger.debug("initDevice: type refEnum: {}", (Object)dataObject);
                JsonArray refers = this.nodeRoot.get("references").getAsJsonArray();
                for (int i = 0; i < refers.size(); ++i) {
                    JsonObject subJSON = refers.get(i).getAsJsonObject();
                    id = subJSON.get("id").getAsString();
                    KM200ServiceHandler serviceHandler = new KM200ServiceHandler(id, this.serviceObject, this.remoteDevice);
                    serviceHandler.initObject();
                }
                break;
            }
            case "moduleList": {
                this.logger.debug("initDevice: type moduleList: {}", (Object)dataObject);
                JsonArray vals = this.nodeRoot.get("values").getAsJsonArray();
                for (int i = 0; i < vals.size(); ++i) {
                    JsonObject subJSON = vals.get(i).getAsJsonObject();
                    id = subJSON.get("id").getAsString();
                    KM200ServiceHandler serviceHandler = new KM200ServiceHandler(id, this.serviceObject, this.remoteDevice);
                    serviceHandler.initObject();
                }
                break;
            }
            case "yRecording": {
                this.logger.debug("initDevice: type yRecording: {}", (Object)dataObject);
                break;
            }
            case "systeminfo": {
                this.logger.debug("initDevice: type systeminfo: {}", (Object)dataObject);
                JsonArray sInfo = this.nodeRoot.get("values").getAsJsonArray();
                this.serviceObject.setValue((Object)sInfo);
                break;
            }
            case "arrayData": {
                this.logger.debug("initDevice: type arrayData: {}", (Object)dataObject);
                this.serviceObject.setJSONData(dataObject);
                break;
            }
            case "eMonitoringList": {
                this.logger.debug("initDevice: type eMonitoringList: {}", (Object)dataObject);
                this.serviceObject.setJSONData(dataObject);
                break;
            }
            case "$$PROTECTED$$": {
                this.logger.debug("initDevice: readonly");
                this.serviceObject.setJSONData(dataObject);
                break;
            }
            default: {
                this.logger.info("initDevice: type: {} unknown for service: {} Data: {}", new Object[]{this.serviceObject.getServiceType(), this.service, dataObject});
            }
        }
        String[] servicePath = this.service.split("/");
        if (this.parent == null) {
            this.remoteDevice.serviceTreeMap.put(servicePath[servicePath.length - 1], this.serviceObject);
        } else {
            this.parent.serviceTreeMap.put(servicePath[servicePath.length - 1], this.serviceObject);
        }
    }
}

