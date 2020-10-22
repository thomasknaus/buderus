/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonParser
 *  javax.xml.bind.DatatypeConverter
 *  org.apache.commons.lang.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.buderus.connection.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KM200Device {
    private final Logger logger = LoggerFactory.getLogger(KM200Device.class);
    private final JsonParser jsonParser = new JsonParser();
    private final KM200Cryption comCryption;
    private final KM200Comm<KM200Device> deviceCommunicator;
    protected String ip4Address;
    protected String gatewayPassword;
    protected String privatePassword;
    protected String charSet;
    protected byte[] cryptKeyInit;
    protected byte[] cryptKeyPriv;
    protected byte[] md5Salt;
    public Map<String, KM200ServiceObject> serviceTreeMap = new HashMap<String, KM200ServiceObject>();
    private List<String> blacklistMap;
    public List<KM200ServiceObject> virtualList;
    protected boolean isIited;

    public KM200Device() {
        this.setBlacklistMap(new ArrayList<String>());
        this.getBlacklistMap().add("/gateway/firmware");
        this.virtualList = new ArrayList<KM200ServiceObject>();
        this.comCryption = new KM200Cryption(this);
        this.deviceCommunicator = new KM200Comm(this);
    }

    public Boolean isConfigured() {
        if (StringUtils.isNotBlank((String)this.ip4Address) && this.cryptKeyPriv != null) {
            return true;
        }
        return false;
    }

    public String getIP4Address() {
        return this.ip4Address;
    }

    public String getGatewayPassword() {
        return this.gatewayPassword;
    }

    public String getPrivatePassword() {
        return this.privatePassword;
    }

    public byte[] getMD5Salt() {
        return this.md5Salt;
    }

    public byte[] getCryptKeyInit() {
        return this.cryptKeyInit;
    }

    public byte[] getCryptKeyPriv() {
        return this.cryptKeyPriv;
    }

    public String getCharSet() {
        return this.charSet;
    }

    public boolean getInited() {
        return this.isIited;
    }

    public List<String> getBlacklistMap() {
        return this.blacklistMap;
    }

    public void setBlacklistMap(List<String> blacklistMap) {
        this.blacklistMap = blacklistMap;
    }

    public void setIP4Address(String ip) {
        this.ip4Address = ip;
    }

    public void setGatewayPassword(String password) {
        this.gatewayPassword = password;
        this.comCryption.recreateKeys();
    }

    public void setPrivatePassword(String password) {
        this.privatePassword = password;
        this.comCryption.recreateKeys();
    }

    public void setMD5Salt(String salt) {
        this.md5Salt = DatatypeConverter.parseHexBinary((String)salt);
        this.comCryption.recreateKeys();
    }

    public void setCryptKeyPriv(String key) {
        this.cryptKeyPriv = DatatypeConverter.parseHexBinary((String)key);
    }

    public void setCryptKeyPriv(byte[] key) {
        this.cryptKeyPriv = key;
    }

    public void setCryptKeyInit(byte[] key) {
        this.cryptKeyInit = key;
    }

    public void setCharSet(String charset) {
        this.charSet = charset;
    }

    public void setInited(boolean inited) {
        this.isIited = inited;
    }

    public void setMaxNbrRepeats(Integer maxNbrRepeats) {
        this.deviceCommunicator.setMaxNbrRepeats(maxNbrRepeats);
    }

    public void listAllServices() {
        if (this.serviceTreeMap != null) {
            this.logger.debug("##################################################################");
            this.logger.debug("List of avalible services");
            this.logger.debug("readable;writeable;recordable;virtual;type;service;value;allowed;min;max;unit");
            this.printAllServices(this.serviceTreeMap);
            this.logger.debug("##################################################################");
        }
    }

    public void printAllServices(Map<String, KM200ServiceObject> actTreeMap) {
        if (actTreeMap != null) {
            for (KM200ServiceObject object : actTreeMap.values()) {
                if (object == null) continue;
                String val = "";
                String valPara = "";
                this.logger.debug("List type: {} service: {}", (Object)object.getServiceType(), (Object)object.getFullServiceName());
                String type = object.getServiceType();
                if (type == null) {
                    type = new String();
                }
                if ("stringValue".equals(type) || "floatValue".equals(type)) {
                    val = object.getValue().toString();
                    if (object.getValueParameter() != null) {
                        List valParas;
                        if ("stringValue".equals(type)) {
                            valParas = (List)object.getValueParameter();
                            for (int i = 0; i < valParas.size(); ++i) {
                                if (i > 0) {
                                    valPara = String.valueOf(valPara) + "|";
                                }
                                valPara = String.valueOf(valPara) + (String)valParas.get(i);
                            }
                            valPara = String.valueOf(valPara) + ";;;";
                        }
                        if ("floatValue".equals(type)) {
                            valParas = (List)object.getValueParameter();
                            valPara = String.valueOf(valPara) + ";";
                            valPara = String.valueOf(valPara) + valParas.get(0);
                            valPara = String.valueOf(valPara) + ";";
                            valPara = String.valueOf(valPara) + valParas.get(1);
                            valPara = String.valueOf(valPara) + ";";
                            if (valParas.size() == 3) {
                                valPara = String.valueOf(valPara) + valParas.get(2);
                            }
                        }
                    } else {
                        valPara = String.valueOf(valPara) + ";;;";
                    }
                } else {
                    val = "";
                    valPara = ";";
                }
                this.logger.debug("{};{};{};{};{};{};{};{}", new Object[]{object.getReadable(), object.getWriteable(), object.getRecordable(), object.getVirtual(), type, object.getFullServiceName(), val, valPara});
                this.printAllServices(object.serviceTreeMap);
            }
        }
    }

    public void resetAllUpdates(Map<String, KM200ServiceObject> actTreeMap) {
        if (actTreeMap != null) {
            for (KM200ServiceObject stmObject : actTreeMap.values()) {
                if (stmObject == null) continue;
                stmObject.setUpdated(false);
                this.resetAllUpdates(stmObject.serviceTreeMap);
            }
        }
    }

    public Boolean containsService(String service) {
        String[] servicePath = service.split("/");
        KM200ServiceObject object = null;
        int len = servicePath.length;
        if (len == 0) {
            return false;
        }
        if (!this.serviceTreeMap.containsKey(servicePath[1])) {
            return false;
        }
        if (len == 2) {
            return true;
        }
        object = this.serviceTreeMap.get(servicePath[1]);
        for (int i = 2; i < len; ++i) {
            if (!object.serviceTreeMap.containsKey(servicePath[i])) {
                return false;
            }
            object = object.serviceTreeMap.get(servicePath[i]);
        }
        return true;
    }

    public KM200ServiceObject getServiceObject(String service) {
        String[] servicePath = service.split("/");
        KM200ServiceObject object = null;
        int len = servicePath.length;
        if (len == 0) {
            return null;
        }
        if (!this.serviceTreeMap.containsKey(servicePath[1])) {
            return null;
        }
        object = this.serviceTreeMap.get(servicePath[1]);
        if (len == 2) {
            return object;
        }
        for (int i = 2; i < len; ++i) {
            if (!object.serviceTreeMap.containsKey(servicePath[i])) {
                return null;
            }
            object = object.serviceTreeMap.get(servicePath[i]);
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JsonObject getServiceNode(String service) {
        String decodedData = null;
        JsonObject nodeRoot = null;
        byte[] recData = this.deviceCommunicator.getDataFromService(service.toString());
        try {
            if (recData == null) {
                this.logger.debug("Communication to {} is not possible!", (Object)service);
                return null;
            }
            if (recData.length == 0) {
                this.logger.debug("No reply from KM200!");
                return null;
            }
            if (recData.length == 1) {
                this.logger.debug("{}: recData.length == 1", (Object)service);
                nodeRoot = new JsonObject();
                decodedData = new String();
                return nodeRoot;
            }
            decodedData = this.comCryption.decodeMessage(recData);
            if (decodedData == null) {
                this.logger.error("Decoding of the KM200 message is not possible!");
                return null;
            }
            if (decodedData.length() <= 0) {
                this.logger.warn("Get empty reply");
                return null;
            }
            if (!"SERVICE NOT AVAILABLE".equals(decodedData)) return (JsonObject)this.jsonParser.parse(decodedData);
            this.logger.debug("{}: SERVICE NOT AVAILABLE", (Object)service);
            return null;
        }
        catch (JsonParseException e) {
            this.logger.error("Parsingexception in JSON: {} service: {}", (Object)e.getMessage(), (Object)service);
            return null;
        }
    }

    public void setServiceNode(String service, JsonObject newObject) {
        this.logger.debug("Encoding: {}", (Object)newObject);
        byte[] encData = this.comCryption.encodeMessage(newObject.toString());
        if (encData == null) {
            this.logger.error("Couldn't encrypt data");
            return;
        }
        this.logger.debug("Send: {}", (Object)service);
        int retVal = this.deviceCommunicator.sendDataToService(service, encData);
        if (retVal == 0) {
            this.logger.debug("Send to device failed: {}: {}", (Object)service, (Object)newObject);
        }
    }
}

