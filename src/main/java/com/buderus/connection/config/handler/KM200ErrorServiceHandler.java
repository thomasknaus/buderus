/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.buderus.connection.config.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class KM200ErrorServiceHandler {
    private final Logger logger = LoggerFactory.getLogger(KM200ErrorServiceHandler.class);
    private Integer activeError = 1;
    private final List<Map<String, String>> errorMap = new ArrayList<Map<String, String>>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void removeAllErrors() {
        List<Map<String, String>> list = this.errorMap;
        synchronized (list) {
            if (this.errorMap != null) {
                this.errorMap.clear();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateErrors(JsonObject nodeRoot) {
        List<Map<String, String>> list = this.errorMap;
        synchronized (list) {
            this.removeAllErrors();
            JsonArray sPoints = nodeRoot.get("values").getAsJsonArray();
            for (int i = 0; i < sPoints.size(); ++i) {
                JsonObject subJSON = sPoints.get(i).getAsJsonObject();
                HashMap valMap = new HashMap();
                Set<Map.Entry<String, JsonElement>> oMap = subJSON.entrySet();
                oMap.forEach(item -> {
                    this.logger.debug("Set: {} val: {}", item.getKey(), (Object)((JsonElement)item.getValue()).getAsString());
                    valMap.put((String)item.getKey(), ((JsonElement)item.getValue()).getAsString());
                });
                this.errorMap.add(valMap);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getNbrErrors() {
        List<Map<String, String>> list = this.errorMap;
        synchronized (list) {
            return this.errorMap.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setActiveError(int error) {
        int actError = error < 1 ? 1 : (error > this.getNbrErrors() ? this.getNbrErrors() : error);
        Integer n = this.activeError;
        synchronized (n) {
            this.activeError = actError;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getActiveError() {
        Integer n = this.activeError;
        synchronized (n) {
            block4: {
                if (this.activeError != null) break block4;
                return 0;
            }
            return this.activeError;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getErrorString() {
        String value = "";
        List<Map<String, String>> list = this.errorMap;
        synchronized (list) {
            int actN;
            block8: {
                actN = this.getActiveError();
                if (this.errorMap.size() >= actN && this.errorMap.size() != 0) break block8;
                return null;
            }
            if (this.errorMap.get(actN - 1).containsKey("t")) {
                value = this.errorMap.get(actN - 1).get("t");
                for (String para : this.errorMap.get(actN - 1).keySet()) {
                    if ("t".equals(para)) continue;
                    value = String.valueOf(value) + " " + para + ":" + this.errorMap.get(actN - 1).get(para);
                }
            } else {
                for (String para : this.errorMap.get(actN - 1).keySet()) {
                    value = String.valueOf(value) + para + ":" + this.errorMap.get(actN - 1).get(para) + " ";
                }
            }
            return value;
        }
    }
}

