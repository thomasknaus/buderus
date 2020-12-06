package com.buderus.controller;

import com.buderus.connection.call.KM200RestCall;
import com.buderus.connection.call.publish.PushTopics;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class KM200RestHelper {

    private final Logger logger = LoggerFactory.getLogger(KM200RestHelper.class);

    protected ResponseEntity turnHeatCuircuitOff(KM200RestCall restCall, String heatService, String ecoService) throws Exception {
        HttpStatus status = HttpStatus.OK;
        switchProgram(restCall, "B", heatService);
        setEcoModeToNull(restCall, ecoService);
        return new ResponseEntity<Boolean>(Boolean.TRUE, status);
    }

    protected ResponseEntity turnHeatOn(KM200RestCall restCall, String service) throws Exception {
        HttpStatus status = HttpStatus.OK;
        switchProgram(restCall, "A", service);
        return new ResponseEntity<Boolean>(Boolean.TRUE, status);
    }

    private void switchProgram(KM200RestCall restCall, String value, String service) throws Exception {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("value", value);
        restCall.doPostRequest(service, jsonObj.toJSONString());
    }

    private void setEcoModeToNull(KM200RestCall restCall, String service) throws Exception {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("value", Float.valueOf(0));
        restCall.doPostRequest(service, jsonObj.toJSONString());
    }
}
