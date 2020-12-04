package com.buderus.controller;

import com.buderus.connection.call.KM200RestCall;
import com.buderus.connection.call.publish.OperationModeHC;
import com.buderus.connection.call.publish.PushTopics;
import com.buderus.connection.call.publish.SuWiSwitchMode;
import com.buderus.connection.call.subscribe.SystemValues;
import com.buderus.connection.config.KM200Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/buderus")
@Api(tags = "buderus")
public class KM200ChangeController implements KM200PutControllerInterface, KM200GetControllerInterface{

    private final Logger logger = LoggerFactory.getLogger(KM200ChangeController.class);

    @Autowired
    private KM200RestCall restCall;

    @Override
    @ApiOperation(value = "Buderus actual supply temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/actualsupttmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KM200Result> getActualSupplyTemperature() {
        KM200Result result = null;
        HttpStatus status = HttpStatus.OK;
        try {
            String message = restCall.doGetRequest(SystemValues.SYSAPPACTSUPTMP.getDescription());
            final ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(message, KM200Result.class);
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<KM200Result>(result, status);
    }

    @Override
    @ApiOperation(value = "Buderus desired supply temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/actualdestmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KM200Result> getDesiredSupplyTemperature() {
        KM200Result result = null;
        HttpStatus status = HttpStatus.OK;
        try {
            String message = restCall.doGetRequest(SystemValues.SYSTMPSUPT1.getDescription());
            final ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(message, KM200Result.class);
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<KM200Result>(result, status);
    }

    @Override
    @ApiOperation(value = "Buderus recieve outdoor temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/outdoortmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KM200Result> getOutdoorTmp() {
        KM200Result result = null;
        HttpStatus status = HttpStatus.OK;
        try {
            String message = restCall.doGetRequest(SystemValues.SYSTMPOUTT1.getDescription());
            final ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(message, KM200Result.class);
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<KM200Result>(result, status);
    }

    @Override
    @ApiOperation(value = "Buderus change operation mode", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = String.class),
            @ApiResponse(code = 204, message = "no content!")})
    @RequestMapping(value = "/chgheatop/{mode}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeHeatOpMode(@ApiParam(value = "operation mode", required = true) @PathVariable("mode") String mode) {
        String message = null;
        HttpStatus status = HttpStatus.OK;
        JSONObject jsonObj = new JSONObject();
        OperationModeHC operationMode = OperationModeHC.contains(mode);
        if(operationMode == null){
            return new ResponseEntity<String>("Operation mode is not valid", HttpStatus.BAD_REQUEST);
        }
        jsonObj.put("value", operationMode.name().toLowerCase());
        try {
            message = restCall.doPostRequest(PushTopics.HEATCIRCUITHC1OPERATIONMODE.getDescription(), jsonObj.toJSONString());
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<String>(message, status);
    }

    @Override
    @ApiOperation(value = "Buderus turn heat off", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = String.class),
            @ApiResponse(code = 204, message = "no content!")})
    @RequestMapping(value = "/turnheatoff", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> turnHeatOff() {
        String message = null;
        HttpStatus status = HttpStatus.OK;
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("value", SuWiSwitchMode.OFF.name().toLowerCase());
        try {
            // wo anders hin
            message = restCall.doPostRequest(PushTopics.HEATCIRCUITHC1SUWISWITCHMODE.getDescription(), jsonObj.toJSONString());
            message = restCall.doPostRequest(PushTopics.HEATCIRCUITHC2SUWISWITCHMODE.getDescription(), jsonObj.toJSONString());
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<String>(message, status);
    }

    @Override
    @ApiOperation(value = "Buderus turn room tmp off", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = String.class),
            @ApiResponse(code = 204, message = "no content!")})
    @RequestMapping(value = "/turnroomtmpoff", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> turnRoomTmpOff() {
        return null;
    }

    @Override
    @ApiOperation(value = "Buderus set room tmp", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = String.class),
            @ApiResponse(code = 204, message = "no content!")})
    @RequestMapping(value = "/chgroomtmp/{tmp}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setRoomTmp(@ApiParam(value = "room temperature", required = true) @PathVariable("tmp") String tmp) {
        return null;
    }

    @Override
    @ApiOperation(value = "Buderus turn heat on", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = String.class),
            @ApiResponse(code = 204, message = "no content!")})
    @RequestMapping(value = "/turnheaton", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> turnHeatOn() {
        return null;
    }
}
