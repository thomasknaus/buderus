package com.buderus.controller;

import com.buderus.connection.call.KM200RestCall;
import com.buderus.connection.call.publish.PublishTopics;
import com.buderus.connection.call.subscribe.HeatCircuit1;
import com.buderus.connection.call.subscribe.WaterCircuit1;
import com.buderus.connection.config.KM200ConnectType;
import com.buderus.connection.config.KM200Converter;
import com.buderus.connection.config.KM200Result;
import com.buderus.connection.config.KM200Status;
import com.buderus.connection.call.subscribe.SystemValues;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        String message = restCall.recieveMessage(SystemValues.SYSAPPACTSUPTMP.getDescription());
        final ObjectMapper mapper = new ObjectMapper();
        KM200Result result = null;
        try {
            result = mapper.readValue(message, KM200Result.class);
        } catch (JsonProcessingException e) {
            logger.error("{}", e.getMessage(), e);
        }
        return new ResponseEntity<KM200Result>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Buderus desired supply temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/actualdesttmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Result> getDesiredSupplyTemperature() {
        String message = restCall.recieveMessage(SystemValues.SYSTMPSUPT1.getDescription());
        final ObjectMapper mapper = new ObjectMapper();
        KM200Result result = null;
        try {
            result = mapper.readValue(message, KM200Result.class);
        } catch (JsonProcessingException e) {
            logger.error("{}", e.getMessage(), e);
        }
        return new ResponseEntity<KM200Result>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Buderus set supply temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/actualsettmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Result> getSetSupplyTemperature() {
        String message = restCall.recieveMessage(SystemValues.SYSTMPSUPT1SET.getDescription());
        final ObjectMapper mapper = new ObjectMapper();
        KM200Result result = null;
        try {
            result = mapper.readValue(message, KM200Result.class);
        } catch (JsonProcessingException e) {
            logger.error("{}", e.getMessage(), e);
        }
        return new ResponseEntity<KM200Result>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Buderus recieve outdoor temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/outdoortmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Result> getOutdoorTmp() {
        String message = restCall.recieveMessage(SystemValues.SYSTMPOUTT1.getDescription());
        final ObjectMapper mapper = new ObjectMapper();
        KM200Result result = null;
        try {
            result = mapper.readValue(message, KM200Result.class);
        } catch (JsonProcessingException e) {
            logger.error("{}", e.getMessage(), e);
        }
        return new ResponseEntity<KM200Result>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Buderus heat circuit temperatur set", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/heatc1tmproomset", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getHeatCircuitRoomTmpSet() {
        String message = restCall.recieveMessage(PublishTopics.HEATCIRCUITHC1TEMPOROOMSET.getDescription());
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }
}
