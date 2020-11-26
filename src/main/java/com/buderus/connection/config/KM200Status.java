package com.buderus.connection.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class KM200Status implements Serializable {

    @JsonProperty("ts")
    private Integer ts;
    @JsonProperty("val")
    private Double val;
    @JsonProperty("km200_unitOfMeasure")
    private String km200UnitOfMeasure;

    @JsonProperty("ts")
    public Integer getTs() {
        return ts;
    }

    @JsonProperty("ts")
    public void setTs(Integer ts) {
        this.ts = ts;
    }

    @JsonProperty("val")
    public Double getVal() {
        return val;
    }

    @JsonProperty("val")
    public void setVal(Double val) {
        this.val = val;
    }

    @JsonProperty("km200_unitOfMeasure")
    public String getKm200UnitOfMeasure() {
        return km200UnitOfMeasure;
    }

    @JsonProperty("km200_unitOfMeasure")
    public void setKm200UnitOfMeasure(String km200UnitOfMeasure) {
        this.km200UnitOfMeasure = km200UnitOfMeasure;
    }
}
