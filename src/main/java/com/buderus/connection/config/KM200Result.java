package com.buderus.connection.config;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "type",
        "writeable",
        "recordable",
        "value",
        "unitOfMeasure",
        "state"
})
public class KM200Result {

    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("writeable")
    private Integer writeable;
    @JsonProperty("recordable")
    private Integer recordable;
    @JsonProperty("value")
    private Double value;
    @JsonProperty("unitOfMeasure")
    private String unitOfMeasure;
    @JsonProperty("state")
    private List<State> state = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("writeable")
    public Integer getWriteable() {
        return writeable;
    }

    @JsonProperty("writeable")
    public void setWriteable(Integer writeable) {
        this.writeable = writeable;
    }

    @JsonProperty("recordable")
    public Integer getRecordable() {
        return recordable;
    }

    @JsonProperty("recordable")
    public void setRecordable(Integer recordable) {
        this.recordable = recordable;
    }

    @JsonProperty("value")
    public Double getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Double value) {
        this.value = value;
    }

    @JsonProperty("unitOfMeasure")
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    @JsonProperty("unitOfMeasure")
    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    @JsonProperty("state")
    public List<State> getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(List<State> state) {
        this.state = state;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}