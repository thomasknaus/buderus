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

    protected ResponseEntity turnHeatOff(KM200RestCall restCall) throws Exception {
        HttpStatus status = HttpStatus.OK;
        switchProgram(restCall, "B");
        return new ResponseEntity<Boolean>(Boolean.TRUE, status);
    }

    protected ResponseEntity turnHeatOn(KM200RestCall restCall) throws Exception {
        HttpStatus status = HttpStatus.OK;
        switchProgram(restCall, "A");
        return new ResponseEntity<Boolean>(Boolean.TRUE, status);
    }

    private void switchProgram(KM200RestCall restCall, String value) throws Exception {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("value", value);
        restCall.doPostRequest(PushTopics.HEATCIRCUITHC1ACTSWITCHPROG.getDescription(), jsonObj.toJSONString());
        restCall.doPostRequest(PushTopics.HEATCIRCUITHC2ACTSWITCHPROG.getDescription(), jsonObj.toJSONString());
    }
}
