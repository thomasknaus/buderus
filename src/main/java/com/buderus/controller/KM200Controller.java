package com.buderus.controller;

import com.buderus.connection.config.KM200ConnectType;
import com.buderus.connection.config.KM200Converter;
import com.buderus.connection.config.KM200Status;
import com.buderus.connection.config.KM200Topic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @Autowired
    private KM200Converter km200Converter;

    @ApiOperation(value = "Buderus connect status", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200ConnectType.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/connected", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200ConnectType> getHealthStatus() {
        KM200ConnectType type = km200Converter.getConnectStatus();
        return new ResponseEntity<KM200ConnectType>(type, HttpStatus.OK);
    }

    @ApiOperation(value = "Buderus chimney", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Status.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/chimney", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Status> getChimney() {
        KM200Status km200Status = km200Converter.getStatusByTopic(KM200Topic.CHIMNEY);
        return new ResponseEntity<KM200Status>(km200Status, HttpStatus.OK);
    }

    @ApiOperation(value = "Buderus outdoor temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Status.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/outtmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Status> getOutdoorTemperature() {
        KM200Status KM200Status = km200Converter.getStatusByTopic(KM200Topic.TMPOUTDOOR);
        return new ResponseEntity<KM200Status>(KM200Status, HttpStatus.OK);
    }

    @ApiOperation(value = "Buderus actual temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Status.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/actualttmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Status> getActualTemperature() {
        KM200Status km200Status = km200Converter.getStatusByTopic(KM200Topic.ACTUALTMP);
        return new ResponseEntity<KM200Status>(km200Status, HttpStatus.OK);
    }

    @ApiOperation(value = "Buderus work time", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Status.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/worktime", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Status> getWorkTime() {
        KM200Status km200Status = km200Converter.getStatusByTopic(KM200Topic.WORKTIME);
        return new ResponseEntity<KM200Status>(km200Status, HttpStatus.OK);
    }

    @ApiOperation(value = "Buderus actual supply temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Status.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/actsupplytmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Status> getActualSupplyTemperature() {
        KM200Status km200Status = km200Converter.getStatusByTopic(KM200Topic.ACTSUPPLYTMP);
        return new ResponseEntity<KM200Status>(km200Status, HttpStatus.OK);
    }

    @ApiOperation(value = "Buderus room temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Status.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/roomtmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Status> getRoomTemperature() {
        KM200Status km200Status = km200Converter.getStatusByTopic(KM200Topic.ROOMTMP);
        return new ResponseEntity<KM200Status>(km200Status, HttpStatus.OK);
    }

    @ApiOperation(value = "Buderus supply setpoint", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Status.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/supplysetpoint", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KM200Status> getSupplySetpoint() {
        KM200Status km200Status = km200Converter.getStatusByTopic(KM200Topic.SUPPLYSETPOINT);
        return new ResponseEntity<KM200Status>(km200Status, HttpStatus.OK);
    }
}