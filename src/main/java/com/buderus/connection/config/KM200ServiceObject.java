/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package com.buderus.connection.config;

import com.google.gson.JsonObject;

import java.util.HashMap;

public class KM200ServiceObject {
    private int readable;
    private int writeable;
    private int recordable;
    private int virtual;
    private boolean updated;
    private String parent;
    private String fullServiceName;
    private String serviceType;
    private JsonObject jsonData;
    private Object value;
    private Object valueParameter;
    public HashMap<String, KM200ServiceObject> serviceTreeMap = new HashMap();
    KM200ServiceObject parentObject;

    public KM200ServiceObject(String fullServiceName, String serviceType, int readable, int writeable, int recordable, int virtual, String parent, KM200ServiceObject parentObject) {
        this.fullServiceName = fullServiceName;
        this.serviceType = serviceType;
        this.readable = readable;
        this.writeable = writeable;
        this.recordable = recordable;
        this.virtual = virtual;
        this.parent = parent;
        this.parentObject = parentObject;
        this.updated = false;
    }

    public void setValue(Object val) {
        this.value = val;
    }

    public void setUpdated(boolean updt) {
        this.updated = updt;
    }

    public void setValueParameter(Object val) {
        this.valueParameter = val;
    }

    public void setJSONData(JsonObject data) {
        this.jsonData = data;
    }

    public int getReadable() {
        return this.readable;
    }

    public int getWriteable() {
        return this.writeable;
    }

    public int getRecordable() {
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

    public int getVirtual() {
        return this.virtual;
    }

    public boolean getUpdated() {
        return this.updated;
    }

    public JsonObject getJSONData() {
        return this.jsonData;
    }
}

