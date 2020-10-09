/*
 * Decompiled with CFR 0.150.
 */
package com.buderus.connection.config;

public class KM200CommObject {
    private Integer readable;
    private Integer writeable;
    private Integer recordable;
    private Integer virtual;
    private Boolean updated = false;
    private String parent;
    private String fullServiceName = "";
    private String serviceType = "";
    private String jsonData = "";
    private Object value = null;
    private Object valueParameter = null;

    public KM200CommObject(String serviceName, String type, Integer read, Integer write, Integer record) {
        this.fullServiceName = serviceName;
        this.serviceType = type;
        this.readable = read;
        this.writeable = write;
        this.recordable = record;
        this.virtual = 0;
        this.parent = null;
    }

    public KM200CommObject(String serviceName, String type, Integer write, Integer record, Integer virtual, String parent) {
        this.fullServiceName = serviceName;
        this.serviceType = type;
        this.readable = 1;
        this.writeable = write;
        this.recordable = record;
        this.virtual = virtual;
        this.parent = parent;
    }

    public KM200CommObject(String serviceName, String type, Integer write, Integer record) {
        this.fullServiceName = serviceName;
        this.serviceType = type;
        this.readable = 1;
        this.writeable = write;
        this.recordable = record;
        this.virtual = 0;
        this.parent = null;
    }

    public void setValue(Object val) {
        this.value = val;
    }

    public void setUpdated(Boolean updt) {
        this.updated = updt;
    }

    public void setValueParameter(Object val) {
        this.valueParameter = val;
    }

    public void setJSONData(String data) {
        this.jsonData = data;
    }

    public Integer getReadable() {
        return this.readable;
    }

    public Integer getWriteable() {
        return this.writeable;
    }

    public Integer getRecordable() {
        return this.recordable;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public String getFullServiceName() {
        return this.fullServiceName;
    }

    public Object getValue() {
        return this.value;
    }

    public Object getValueParameter() {
        return this.valueParameter;
    }

    public String getParent() {
        return this.parent;
    }

    public Integer getVirtual() {
        return this.virtual;
    }

    public Boolean getUpdated() {
        return this.updated;
    }

    public String getJSONData() {
        return this.jsonData;
    }
}

