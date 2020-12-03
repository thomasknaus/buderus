package com.buderus.controller;

import com.buderus.connection.call.KM200RestCall;
import com.buderus.connection.call.publish.OperationModeHC;
import com.buderus.connection.call.publish.PushTopics;
import com.buderus.connection.call.subscribe.SystemValues;
import com.buderus.connection.config.KM200Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/buderus")
@Api(tags = "buderus")
public class KM200Controller {

    private final Logger logger = LoggerFactory.getLogger(KM200Controller.class);

    @Autowired
    private KM200RestCall restCall;

    @ApiOperation(value = "Buderus actual supply temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/actualsupttmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Result> getActualSupplyTemperature() {
        KM200Result result = null;
        HttpStatus status = HttpStatus.OK;
        try {
            String message = restCall.doGetRequest(SystemValues.SYSAPPACTSUPTMP.getDescription());
            final ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(message, KM200Result.class);
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<KM200Result>(result, status);
    }

    @ApiOperation(value = "Buderus desired supply temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/actualdesttmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Result> getDesiredSupplyTemperature() {
        KM200Result result = null;
        HttpStatus status = HttpStatus.OK;
        try {
            String message = restCall.doGetRequest(SystemValues.SYSTMPSUPT1.getDescription());
            final ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(message, KM200Result.class);
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<KM200Result>(result, status);
    }

    @ApiOperation(value = "Buderus set supply temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/actualsettmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Result> getSetSupplyTemperature() {
        KM200Result result = null;
        HttpStatus status = HttpStatus.OK;
        try {
            String message = restCall.doGetRequest(SystemValues.SYSTMPSUPT1SET.getDescription());
            final ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(message, KM200Result.class);
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<KM200Result>(result, status);
    }

    @ApiOperation(value = "Buderus recieve outdoor temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/outdoortmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Result> getOutdoorTmp() {
        KM200Result result = null;
        HttpStatus status = HttpStatus.OK;
        try {
            String message = restCall.doGetRequest(SystemValues.SYSTMPOUTT1.getDescription());
            final ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(message, KM200Result.class);
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<KM200Result>(result, status);
    }

    @ApiOperation(value = "Buderus heat circuit temperatur set", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/heatc1suwi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getHeatCircuitSuWi() {
        String message = null;
        HttpStatus status = HttpStatus.OK;
        try {
            message = restCall.doGetRequest(PushTopics.HEATCIRCUITHC1SUWITHREESHOLD.getDescription());
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<String>(message, status);
    }

    @ApiOperation(value = "Buderus set heat circuit temperatur", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/heatc1suwiset", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> setHeatCircuitSuWi() {
        String message = null;
        HttpStatus status = HttpStatus.OK;
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("value", Float.valueOf(10));
        try {
            message = restCall.doPostRequest(PushTopics.HEATCIRCUITHC1SUWITHREESHOLD.getDescription(), jsonObj.toJSONString());
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<String>(message, status);
    }

    @ApiOperation(value = "Buderus set operation mode", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/setopmode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> setHeatOpMode() {

        String message = null;
        HttpStatus status = HttpStatus.OK;
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("value", OperationModeHC.MANUAL.name().toLowerCase());
        try {
            message = restCall.doPostRequest(PushTopics.HEATCIRCUITHC1OPERATIONMODE.getDescription(), jsonObj.toJSONString());
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<String>(message, status);
    }

    @ApiOperation(value = "Buderus get operation mode", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/getopmode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getHeatOpMode() {

        String message = null;
        HttpStatus status = HttpStatus.OK;
        try {
            message = restCall.doGetRequest(PushTopics.HEATCIRCUITHC1OPERATIONMODE.getDescription());
        } catch (IOException e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<String>(message, status);
    }
}
