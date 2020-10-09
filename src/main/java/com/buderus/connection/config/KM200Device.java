/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.DatatypeConverter
 *  org.apache.commons.lang.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.buderus.connection.config;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KM200Device {
    private static final Logger logger = LoggerFactory.getLogger(KM200Device.class);
    protected String ip4Address = null;
    protected String gatewayPassword = null;
    protected String privatePassword = null;
    protected String charSet = null;
    protected byte[] cryptKeyInit = null;
    protected byte[] cryptKeyPriv = null;
    protected byte[] MD5Salt = null;
    HashMap<String, KM200CommObject> serviceMap = new HashMap();
    List<String> blacklistMap = new ArrayList<String>();
    List<KM200CommObject> virtualList = null;
    protected Boolean inited = false;

    public KM200Device() {
        this.blacklistMap.add("/gateway/firmware");
        this.virtualList = new ArrayList<KM200CommObject>();
    }

    public Boolean isConfigured() {
        if (StringUtils.isNotBlank((String)this.ip4Address) && this.cryptKeyPriv != null) {
            return true;
        }
        return false;
    }

    private void RecreateKeys() {
        if (StringUtils.isNotBlank((String)this.gatewayPassword) && StringUtils.isNotBlank((String)this.privatePassword) && this.MD5Salt != null) {
            byte[] MD5_K1 = null;
            byte[] MD5_K2_Init = null;
            byte[] MD5_K2_Private = null;
            byte[] bytesOfGatewayPassword = null;
            byte[] bytesOfPrivatePassword = null;
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            }
            catch (NoSuchAlgorithmException e) {
                logger.error("No such algorithm, MD5: {}", (Object)e.getMessage());
            }
            bytesOfGatewayPassword = this.gatewayPassword.getBytes(StandardCharsets.UTF_8);
            byte[] CombParts1 = new byte[bytesOfGatewayPassword.length + this.MD5Salt.length];
            System.arraycopy(bytesOfGatewayPassword, 0, CombParts1, 0, bytesOfGatewayPassword.length);
            System.arraycopy(this.MD5Salt, 0, CombParts1, bytesOfGatewayPassword.length, this.MD5Salt.length);
            MD5_K1 = md.digest(CombParts1);
            MD5_K2_Init = md.digest(this.MD5Salt);
            bytesOfPrivatePassword = this.privatePassword.getBytes(StandardCharsets.UTF_8);
            byte[] CombParts2 = new byte[bytesOfPrivatePassword.length + this.MD5Salt.length];
            System.arraycopy(this.MD5Salt, 0, CombParts2, 0, this.MD5Salt.length);
            System.arraycopy(bytesOfPrivatePassword, 0, CombParts2, this.MD5Salt.length, bytesOfPrivatePassword.length);
            MD5_K2_Private = md.digest(CombParts2);
            this.cryptKeyInit = new byte[MD5_K1.length + MD5_K2_Init.length];
            System.arraycopy(MD5_K1, 0, this.cryptKeyInit, 0, MD5_K1.length);
            System.arraycopy(MD5_K2_Init, 0, this.cryptKeyInit, MD5_K1.length, MD5_K2_Init.length);
            this.cryptKeyPriv = new byte[MD5_K1.length + MD5_K2_Private.length];
            System.arraycopy(MD5_K1, 0, this.cryptKeyPriv, 0, MD5_K1.length);
            System.arraycopy(MD5_K2_Private, 0, this.cryptKeyPriv, MD5_K1.length, MD5_K2_Private.length);
        }
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

    public byte[] getCryptKeyInit() {
        return this.cryptKeyInit;
    }

    public byte[] getCryptKeyPriv() {
        return this.cryptKeyPriv;
    }

    public String getCharSet() {
        return this.charSet;
    }

    public Boolean getInited() {
        return this.inited;
    }

    public void listAllServices() {
        if (this.serviceMap != null) {
            logger.info("##################################################################");
            logger.info("List of avalible services");
            logger.info("readable;writeable;recordable;virtual;type;service;value;allowed;min;max");
            for (KM200CommObject object : this.serviceMap.values()) {
                if (object == null) continue;
                String val = "";
                String valPara = "";
                logger.debug("List type: {} service: {}", (Object)object.getServiceType(), (Object)object.getFullServiceName());
                String type = object.getServiceType();
                if (type == null) {
                    type = new String();
                }
                if (type.equals("stringValue") || type.equals("floatValue")) {
                    val = object.getValue().toString();
                    if (object.getValueParameter() != null) {
                        List valParas;
                        if (type.equals("stringValue")) {
                            valParas = (List)object.getValueParameter();
                            for (int i = 0; i < valParas.size(); ++i) {
                                if (i > 0) {
                                    valPara = String.valueOf(valPara) + "|";
                                }
                                valPara = String.valueOf(valPara) + (String)valParas.get(i);
                            }
                            valPara = String.valueOf(valPara) + ";;";
                        }
                        if (type.equals("floatValue")) {
                            valParas = (List)object.getValueParameter();
                            valPara = String.valueOf(valPara) + ";";
                            if (valParas.size() == 2) {
                                valPara = String.valueOf(valPara) + valParas.get(0);
                                valPara = String.valueOf(valPara) + ";";
                                valPara = String.valueOf(valPara) + valParas.get(1);
                            } else {
                                logger.debug("Value parameter for float != 2, this shouldn't happen");
                                valPara = String.valueOf(valPara) + ";";
                            }
                        }
                    } else {
                        valPara = String.valueOf(valPara) + ";;";
                    }
                } else {
                    val = "";
                    valPara = ";";
                }
                logger.info("{};{};{};{};{};{};{};{}", new Object[]{object.getReadable().toString(), object.getWriteable().toString(), object.getRecordable().toString(), object.getVirtual().toString(), type, object.getFullServiceName(), val, valPara});
            }
            logger.info("##################################################################");
        }
    }

    public void resetAllUpdates() {
        if (this.serviceMap != null) {
            for (KM200CommObject object : this.serviceMap.values()) {
                if (object == null) continue;
                object.setUpdated(false);
            }
        }
    }

    public void setIP4Address(String ip) {
        this.ip4Address = ip;
    }

    public void setGatewayPassword(String password) {
        this.gatewayPassword = password;
        this.RecreateKeys();
    }

    public void setPrivatePassword(String password) {
        this.privatePassword = password;
        this.RecreateKeys();
    }

    public void setMD5Salt(String salt) {
        this.MD5Salt = DatatypeConverter.parseHexBinary((String)salt);
        this.RecreateKeys();
    }

    public void setCryptKeyPriv(String key) {
        this.cryptKeyPriv = DatatypeConverter.parseHexBinary((String)key);
    }

    public void setCharSet(String charset) {
        this.charSet = charset;
    }

    public void setInited(Boolean Init) {
        this.inited = Init;
    }
}

