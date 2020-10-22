package com.buderus.controller;

import com.buderus.connection.call.BuderusConnectionCall;
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
public class HealthStatusController {

    @Autowired
    private BuderusConnectionCall buderusConnectionCall;

    @ApiOperation(value = "Buderus activities", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = Boolean.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/healthCheck", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Boolean> getHealthStatus() {
        buderusConnectionCall.initialize();
        boolean configured = buderusConnectionCall.getDevice().isConfigured();
        return new ResponseEntity<>(configured, HttpStatus.OK);
    }
}
