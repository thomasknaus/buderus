/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonParser
 *  org.eclipse.smarthome.core.library.types.DateTimeType
 *  org.eclipse.smarthome.core.library.types.DecimalType
 *  org.eclipse.smarthome.core.library.types.OnOffType
 *  org.eclipse.smarthome.core.library.types.StringType
 *  org.eclipse.smarthome.core.types.Command
 *  org.eclipse.smarthome.core.types.State
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.buderus.connection.config.handler;

import com.buderus.connection.config.KM200Device;
import com.buderus.connection.config.KM200ServiceObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.eclipse.smarthome.core.library.types.DateTimeType;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KM200DataHandler {
    private final Logger logger = LoggerFactory.getLogger(KM200DataHandler.class);
    private final JsonParser jsonParser = new JsonParser();
    private final KM200Device remoteDevice;

    public KM200DataHandler(KM200Device remoteDevice) {
        this.remoteDevice = remoteDevice;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public State getProvidersState(String service, String itemType, Map<String, String> itemPara) {
        KM200Device kM200Device = this.remoteDevice;
        synchronized (kM200Device) {
            JsonObject jsonNode;
            String type = null;
            KM200ServiceObject object = null;
            this.logger.debug("Check state of: {}  item: {}", (Object)service, (Object)itemType);
            if (this.remoteDevice.getBlacklistMap().contains(service)) {
                this.logger.debug("Service on blacklist: {}", (Object)service);
                return null;
            }
            if (!this.remoteDevice.containsService(service).booleanValue()) {
                this.logger.warn("Service is not in the determined device service list: {}", (Object)service);
                return null;
            }
            object = this.remoteDevice.getServiceObject(service);
            if (object.getReadable() == 0) {
                this.logger.warn("Service is listed as protected (reading is not possible): {}", (Object)service);
                return null;
            }
            type = object.getServiceType();
            if (!object.getUpdated() || object.getVirtual() == 1 && !this.remoteDevice.getServiceObject(object.getParent()).getUpdated()) {
                if (object.getVirtual() == 1) {
                    this.logger.debug("Receive data for an virtual object");
                    jsonNode = this.remoteDevice.getServiceNode(object.getParent());
                } else {
                    this.logger.debug("Receive data");
                    jsonNode = this.remoteDevice.getServiceNode(service);
                }
                if (jsonNode == null || jsonNode.isJsonNull()) {
                    this.logger.error("Communication is not possible!");
                    return null;
                }
                if (object.getVirtual() == 1) {
                    this.remoteDevice.getServiceObject(object.getParent()).setJSONData(jsonNode);
                    this.remoteDevice.getServiceObject(object.getParent()).setUpdated(true);
                } else {
                    object.setJSONData(jsonNode);
                }
                object.setUpdated(true);
            } else if (object.getVirtual() == 1) {
                this.logger.debug("Get data for an virtual object");
                jsonNode = this.remoteDevice.getServiceObject(object.getParent()).getJSONData();
            } else {
                this.logger.debug("Get data");
                jsonNode = object.getJSONData();
            }
            return this.parseJSONData(jsonNode, type, service, itemType, itemPara);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public State parseJSONData(JsonObject nodeRoot, String type, String service, String itemType, Map<String, String> itemPara) {
        DecimalType state = null;
        KM200ServiceObject object = this.remoteDevice.getServiceObject(service);
        this.logger.debug("parseJSONData service: {}, data: {}", (Object)service, (Object)nodeRoot);
        try {
            block41: {
                block39: {
                    block43: {
                        block44: {
                            block42: {
                                block45: {
                                    block40: {
                                        if (nodeRoot.toString().length() == 2) {
                                            this.logger.warn("Get empty reply");
                                            return null;
                                        }
                                        String string = type;
                                        switch (string.hashCode()) {
                                            case -1519213600: {
                                                if (string.equals("stringValue")) break;
                                                return null;
                                            }
                                            case -1425407581: {
                                                if (!string.equals("arrayData")) {
                                                    return null;
                                                }
                                                break block39;
                                            }
                                            case -1025730443: {
                                                if (!string.equals("floatValue")) {
                                                    return null;
                                                }
                                                break block40;
                                            }
                                            case -547021621: {
                                                if (!string.equals("eMonitoringList")) {
                                                    return null;
                                                }
                                                break block41;
                                            }
                                            case 329298630: {
                                                if (!string.equals("errorList")) {
                                                    return null;
                                                }
                                                break block42;
                                            }
                                            case 643508061: {
                                                if (!string.equals("systeminfo")) {
                                                    return null;
                                                }
                                                break block43;
                                            }
                                            case 694228344: {
                                                if (!string.equals("yRecording")) {
                                                    return null;
                                                }
                                                break block44;
                                            }
                                            case 1446853808: {
                                                if (!string.equals("switchProgram")) {
                                                    return null;
                                                }
                                                break block45;
                                            }
                                        }
                                        this.logger.debug("parseJSONData type string value: {} Type: {}", (Object)nodeRoot, (Object)itemType.toString());
                                        String sVal = nodeRoot.get("value").getAsString();
                                        object.setValue(sVal);
                                        if ("Switch".equals(itemType)) {
                                            Map<String, String> switchNames = itemPara;
                                            if (switchNames.containsKey("on")) {
                                                if (sVal.equals(switchNames.get("off"))) {
                                                    return OnOffType.OFF;
                                                }
                                                if (!sVal.equals(switchNames.get("on"))) return state;
                                                return OnOffType.ON;
                                            }
                                            if (switchNames.isEmpty()) {
                                                this.logger.debug("No switch item configuration");
                                                return null;
                                            }
                                            this.logger.warn("Switch-Item only on configured on/off string values: {}", (Object)nodeRoot);
                                            return null;
                                        }
                                        if ("Number".equals(itemType)) {
                                            try {
                                                return new DecimalType((double)Float.parseFloat(sVal));
                                            }
                                            catch (NumberFormatException e) {
                                                this.logger.error("Conversion of the string value to Decimal wasn't possible, data: {} error: {}", (Object)nodeRoot, (Object)e.getMessage());
                                                return null;
                                            }
                                        }
                                        if ("DateTime".equals(itemType)) {
                                            try {
                                                return new DateTimeType(sVal);
                                            }
                                            catch (IllegalArgumentException e) {
                                                this.logger.error("Conversion of the string value to DateTime wasn't possible, data: {} error: {}", (Object)nodeRoot, (Object)e.getMessage());
                                                return null;
                                            }
                                        }
                                        if ("String".equals(itemType)) {
                                            return new StringType(sVal);
                                        }
                                        this.logger.warn("Bindingtype not supported for string values: {}", itemType.getClass());
                                        return null;
                                    }
                                    this.logger.debug("state of type float value: {}", (Object)nodeRoot);
                                    Object bdVal = null;
                                    bdVal = nodeRoot.get("value");
                                    try {
                                        bdVal = nodeRoot.get("value").getAsBigDecimal();
                                    }
                                    catch (NumberFormatException numberFormatException) {
                                        this.logger.debug("float value is a string: {}", bdVal);
                                        bdVal = Double.NaN;
                                    }
                                    object.setValue(bdVal);
                                    if ("Number".equals(itemType)) {
                                        if (!(bdVal instanceof Double)) return new DecimalType((double)((BigDecimal)bdVal).floatValue());
                                        return new DecimalType((double)((Double)bdVal).floatValue());
                                    }
                                    if ("String".equals(itemType)) {
                                        return new StringType(bdVal.toString());
                                    }
                                    this.logger.warn("Bindingtype not supported for float values: {}", itemType.getClass());
                                    return null;
                                }
                                KM200SwitchProgramServiceHandler sPService = null;
                                this.logger.debug("state of type switchProgram: {}", (Object)nodeRoot);
                                sPService = object.getVirtual() == 0 ? (KM200SwitchProgramServiceHandler)object.getValueParameter() : (KM200SwitchProgramServiceHandler)this.remoteDevice.getServiceObject(object.getParent()).getValueParameter();
                                sPService.updateSwitches(nodeRoot, this.remoteDevice);
                                if (object.getVirtual() == 1) {
                                    return this.getVirtualState(object, itemType, service);
                                }
                                if ("String".equals(itemType)) {
                                    return new StringType(nodeRoot.get("switchPoints").getAsJsonArray().toString());
                                }
                                this.logger.warn("Bindingtype not supported for switchProgram, only json over strings supported: {}", itemType.getClass());
                                return null;
                            }
                            KM200ErrorServiceHandler eService = null;
                            this.logger.debug("state of type errorList: {}", (Object)nodeRoot);
                            eService = object.getVirtual() == 0 ? (KM200ErrorServiceHandler)object.getValueParameter() : (KM200ErrorServiceHandler)this.remoteDevice.getServiceObject(object.getParent()).getValueParameter();
                            eService.updateErrors(nodeRoot);
                            if (object.getVirtual() == 1) {
                                return this.getVirtualState(object, itemType, service);
                            }
                            if ("String".equals(itemType)) {
                                return new StringType(nodeRoot.get("values").getAsJsonArray().toString());
                            }
                            this.logger.warn("Bindingtype not supported for error list, only json over strings is supported: {}", itemType.getClass());
                            return null;
                        }
                        this.logger.info("state of: type yRecording is not supported yet: {}", (Object)nodeRoot);
                        return null;
                    }
                    this.logger.info("state of: type systeminfo is not supported yet: {}", (Object)nodeRoot);
                    return null;
                }
                this.logger.info("state of: type arrayData is not supported yet: {}", (Object)nodeRoot);
                return null;
            }
            this.logger.info("state of: type eMonitoringList is not supported yet: {}", (Object)nodeRoot);
            return null;
        }
        catch (JsonParseException e) {
            this.logger.error("Parsingexception in JSON, data: {} error: {} ", (Object)nodeRoot, (Object)e.getMessage());
        }
        return null;
    }

    private State getVirtualState(KM200ServiceObject object, String itemType, String service) {
        StringType state = null;
        String type = object.getServiceType();
        this.logger.debug("Check virtual state of: {} type: {} item: {}", new Object[]{service, type, itemType});
        block4 : switch (type) {
            case "switchProgram": {
                KM200SwitchProgramServiceHandler sPService = (KM200SwitchProgramServiceHandler)this.remoteDevice.getServiceObject(object.getParent()).getValueParameter();
                String[] servicePath = service.split("/");
                String virtService = servicePath[servicePath.length - 1];
                if ("weekday".equals(virtService)) {
                    if ("String".equals(itemType)) {
                        String val = sPService.getActiveDay();
                        if (val == null) {
                            return null;
                        }
                        state = new StringType(val);
                        break;
                    }
                    this.logger.warn("Bindingtype not supported for day service: {}", itemType.getClass());
                    return null;
                }
                if ("nbrCycles".equals(virtService)) {
                    if ("Number".equals(itemType)) {
                        Integer val = sPService.getNbrCycles();
                        if (val == null) {
                            return null;
                        }
                        state = new StringType(val.toString());
                        break;
                    }
                    this.logger.warn("Bindingtype not supported for nbrCycles service: {}", itemType.getClass());
                    return null;
                }
                if ("cycle".equals(virtService)) {
                    if ("Number".equals(itemType)) {
                        Integer val = sPService.getActiveCycle();
                        if (val == null) {
                            return null;
                        }
                        state = new StringType(val.toString());
                        break;
                    }
                    this.logger.warn("Bindingtype not supported for cycle service: {}", itemType.getClass());
                    return null;
                }
                if (virtService.equals(sPService.getPositiveSwitch())) {
                    if ("Number".equals(itemType)) {
                        Integer val = sPService.getActivePositiveSwitch();
                        if (val == null) {
                            return null;
                        }
                        state = new StringType(val.toString());
                        break;
                    }
                    if ("DateTime".equals(itemType)) {
                        Integer val = sPService.getActivePositiveSwitch();
                        if (val == null) {
                            return null;
                        }
                        Calendar rightNow = Calendar.getInstance();
                        Integer hour = val % 60;
                        Integer minute = val - hour * 60;
                        rightNow.set(11, hour);
                        rightNow.set(12, minute);
                        state = new StringType(rightNow.toString());
                        break;
                    }
                    this.logger.warn("Bindingtype not supported for cycle service: {}", (Object)itemType);
                    return null;
                }
                if (!virtService.equals(sPService.getNegativeSwitch())) break;
                if ("Number".equals(itemType)) {
                    Integer val = sPService.getActiveNegativeSwitch();
                    if (val == null) {
                        return null;
                    }
                    state = new StringType(val.toString());
                    break;
                }
                if ("DateTime".equals(itemType)) {
                    Integer val = sPService.getActiveNegativeSwitch();
                    if (val == null) {
                        return null;
                    }
                    Calendar rightNow = Calendar.getInstance();
                    Integer hour = val % 60;
                    Integer minute = val - hour * 60;
                    rightNow.set(11, hour);
                    rightNow.set(12, minute);
                    state = new StringType(rightNow.toString());
                    break;
                }
                this.logger.warn("Bindingtype not supported for cycle service: {}", itemType.getClass());
                return null;
            }
            case "errorList": {
                String nVirtService;
                KM200ErrorServiceHandler eService = (KM200ErrorServiceHandler)this.remoteDevice.getServiceObject(object.getParent()).getValueParameter();
                String[] nServicePath = service.split("/");
                switch (nVirtService = nServicePath[nServicePath.length - 1]) {
                    case "nbrErrors": {
                        if ("Number".equals(itemType)) {
                            Integer val = eService.getNbrErrors();
                            state = new StringType(val.toString());
                            break block4;
                        }
                        this.logger.warn("Bindingtype not supported for error number service: {}", itemType.getClass());
                        return null;
                    }
                    case "error": {
                        if ("Number".equals(itemType)) {
                            Integer val = eService.getActiveError();
                            state = new StringType(val.toString());
                            break block4;
                        }
                        this.logger.warn("Bindingtype not supported for error service: {}", itemType.getClass());
                        return null;
                    }
                    case "errorString": {
                        if ("String".equals(itemType)) {
                            String val = eService.getErrorString();
                            if (val == null) {
                                return null;
                            }
                            state = new StringType(val);
                            break block4;
                        }
                        this.logger.warn("Bindingtype not supported for error string service: {}", itemType.getClass());
                        return null;
                    }
                }
            }
        }
        return state;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JsonObject sendProvidersState(String service, Command command, String itemType, Object itemPara) {
        KM200Device kM200Device = this.remoteDevice;
        synchronized (kM200Device) {
            JsonObject newObject;
            this.logger.debug("Prepare item for send: {} zitem: {}", (Object)service, (Object)itemType);
            if (this.remoteDevice.getBlacklistMap().contains(service)) {
                this.logger.debug("Service on blacklist: {}", (Object)service);
                return null;
            }
            if (!this.remoteDevice.containsService(service).booleanValue()) {
                this.logger.error("Service is not in the determined device service list: {}", (Object)service);
                return null;
            }
            if (this.remoteDevice.getServiceObject(service).getWriteable() == 0) {
                this.logger.error("Service is listed as read-only: {}", (Object)service);
                return null;
            }
            KM200ServiceObject object = this.remoteDevice.getServiceObject(service);
            String type = object.getServiceType();
            this.logger.debug("state of: {} type: {}", (Object)command, (Object)type);
            if ("Number".equals(itemType)) {
                BigDecimal bdVal = ((DecimalType)command).toBigDecimal();
                this.logger.debug("val: {}", (Object)bdVal);
                if (object.getValueParameter() != null) {
                    List valParas = (List)object.getValueParameter();
                    BigDecimal minVal = (BigDecimal)valParas.get(0);
                    BigDecimal maxVal = (BigDecimal)valParas.get(1);
                    if (bdVal.compareTo(minVal) < 0) {
                        bdVal = minVal;
                    }
                    if (bdVal.compareTo(maxVal) > 0) {
                        bdVal = maxVal;
                    }
                }
                newObject = new JsonObject();
                if ("floatValue".equals(type)) {
                    newObject.addProperty("value", (Number)bdVal);
                } else if ("stringValue".equals(type)) {
                    newObject.addProperty("value", bdVal.toString());
                } else if ("switchProgram".equals(type) && object.getVirtual() == 1) {
                    newObject = this.sendVirtualState(object, service, command, itemType);
                } else if ("errorList".equals(type) && object.getVirtual() == 1) {
                    newObject = this.sendVirtualState(object, service, command, itemType);
                } else {
                    this.logger.warn("Not supported type for numberItem: {}", (Object)type);
                }
            } else if ("String".equals(itemType)) {
                List valParas;
                String val = ((StringType)command).toString();
                newObject = new JsonObject();
                if (object.getValueParameter() != null && !(valParas = (List)object.getValueParameter()).contains(val)) {
                    this.logger.warn("Parameter is not in the service parameterlist: {}", (Object)val);
                    return null;
                }
                if ("stringValue".equals(type)) {
                    newObject.addProperty("value", val);
                } else if ("floatValue".equals(type)) {
                    newObject.addProperty("value", (Number)Float.valueOf(Float.parseFloat(val)));
                } else if ("switchProgram".equals(type)) {
                    if (object.getVirtual() == 1) {
                        newObject = this.sendVirtualState(object, service, command, itemType);
                    } else {
                        try {
                            JsonArray userArray = (JsonArray)this.jsonParser.parse(val);
                            newObject = userArray.getAsJsonObject();
                        }
                        catch (JsonParseException e) {
                            this.logger.warn("The input for the switchProgram is not a valid JSONArray : {}", (Object)e.getMessage());
                            return null;
                        }
                    }
                } else {
                    this.logger.warn("Not supported type for stringItem: {}", (Object)type);
                }
            } else if ("DateTime".equals(itemType)) {
                String val = ((DateTimeType)command).toString();
                newObject = new JsonObject();
                if ("stringValue".equals(type)) {
                    newObject.addProperty("value", val);
                } else if ("switchProgram".equals(type)) {
                    newObject = this.sendVirtualState(object, service, command, itemType);
                } else {
                    this.logger.warn("Not supported type for dateTimeItem: {}", (Object)type);
                }
            } else {
                if (!"Switch".equals(itemType)) {
                    this.logger.warn("Bindingtype not supported: {}", itemType.getClass());
                    return null;
                }
                String val = null;
                newObject = new JsonObject();
                HashMap switchNames = (HashMap)itemPara;
                if (switchNames.containsKey("on")) {
                    if (command == OnOffType.OFF) {
                        val = (String)switchNames.get("off");
                    } else if (command == OnOffType.ON) {
                        val = (String)switchNames.get("on");
                    }
                    List valParas = (List)object.getValueParameter();
                    if (!valParas.contains(val)) {
                        this.logger.warn("Parameter is not in the service parameterlist: {}", (Object)val);
                        return null;
                    }
                } else {
                    if (switchNames.isEmpty()) {
                        this.logger.debug("No switch item configuration");
                        return null;
                    }
                    this.logger.warn("Switch-Item only on configured on/off string values {}", (Object)command);
                    return null;
                }
                if ("stringValue".equals(type)) {
                    newObject.addProperty("value", val);
                } else {
                    this.logger.warn("Not supported type for SwitchItem:{}", (Object)type);
                }
            }
            if (newObject != null && newObject.toString().length() > 2) {
                this.logger.debug("Send Data: {}", (Object)newObject);
                return newObject;
            }
            return null;
        }
    }

    public JsonObject sendVirtualState(KM200ServiceObject object, String service, Command command, String itemType) {
        JsonObject newObject;
        block22: {
            KM200ServiceObject parObject;
            block23: {
                String type;
                block21: {
                    newObject = null;
                    type = null;
                    this.logger.debug("Check virtual state of: {} type: {} item: {}", new Object[]{service, type, itemType});
                    parObject = this.remoteDevice.getServiceObject(object.getParent());
                    type = object.getServiceType();
                    if (!"String".equals(itemType)) break block21;
                    String val = ((StringType)command).toString();
                    String string = type;
                    switch (string.hashCode()) {
                        case 1446853808: {
                            if (!string.equals("switchProgram")) break;
                            KM200SwitchProgramServiceHandler sPService = (KM200SwitchProgramServiceHandler)parObject.getValueParameter();
                            String[] servicePath = service.split("/");
                            String virtService = servicePath[servicePath.length - 1];
                            if (!"weekday".equals(virtService)) break block22;
                            sPService.setActiveDay(val);
                        }
                        default: {
                            break;
                        }
                    }
                    break block22;
                }
                if (!"Number".equals(itemType)) break block23;
                Integer val = ((DecimalType)command).intValue();
                String string = type;
                switch (string.hashCode()) {
                    case 329298630: {
                        if (string.equals("errorList")) break;
                        break block22;
                    }
                    case 1446853808: {
                        if (string.equals("switchProgram")) {
                            KM200SwitchProgramServiceHandler sPService = (KM200SwitchProgramServiceHandler)parObject.getValueParameter();
                            String[] servicePath = service.split("/");
                            String virtService = servicePath[servicePath.length - 1];
                            if ("cycle".equals(virtService)) {
                                sPService.setActiveCycle(val);
                            } else if (virtService.equals(sPService.getPositiveSwitch())) {
                                sPService.setActivePositiveSwitch(val);
                                newObject = sPService.getUpdatedJSONData(parObject);
                            } else if (virtService.equals(sPService.getNegativeSwitch())) {
                                sPService.setActiveNegativeSwitch(val);
                                newObject = sPService.getUpdatedJSONData(parObject);
                            }
                        }
                        break block22;
                    }
                }
                KM200ErrorServiceHandler eService = (KM200ErrorServiceHandler)this.remoteDevice.getServiceObject(object.getParent()).getValueParameter();
                String[] nServicePath = service.split("/");
                String nVirtService = nServicePath[nServicePath.length - 1];
                if ("error".equals(nVirtService)) {
                    eService.setActiveError(val);
                }
                break block22;
            }
            if ("DateTime".equals(itemType)) {
                Integer minutes;
                Calendar cal = ((DateTimeType)command).getCalendar();
                KM200SwitchProgramServiceHandler sPService = (KM200SwitchProgramServiceHandler)parObject.getValueParameter();
                String[] servicePath = service.split("/");
                String virtService = servicePath[servicePath.length - 1];
                if (virtService.equals(sPService.getPositiveSwitch())) {
                    minutes = cal.get(11) * 60 + cal.get(12);
                    minutes = minutes % sPService.getSwitchPointTimeRaster() * sPService.getSwitchPointTimeRaster();
                    sPService.setActivePositiveSwitch(minutes);
                    newObject = sPService.getUpdatedJSONData(parObject);
                }
                if (virtService.equals(sPService.getNegativeSwitch())) {
                    minutes = cal.get(11) * 60 + cal.get(12);
                    minutes = minutes % sPService.getSwitchPointTimeRaster() * sPService.getSwitchPointTimeRaster();
                    sPService.setActiveNegativeSwitch(minutes);
                    newObject = sPService.getUpdatedJSONData(parObject);
                }
            }
        }
        return newObject;
    }
}

