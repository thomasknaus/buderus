package com.buderus.controller;

import com.buderus.connection.call.KM200RestCall;
import com.buderus.connection.call.publish.Circuit;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/api/buderus")
@Api(tags = "buderus")
public class KM200ChangeController extends KM200RestHelper implements KM200PutControllerInterface, KM200GetControllerInterface {

    private final Logger logger = LoggerFactory.getLogger(KM200ChangeController.class);

    @Autowired
    private KM200RestCall restCall;

    @Override
    public ResponseEntity<KM200Result> getActualPower() {
        return null;
    }

    @Override
    @ApiOperation(value = "Buderus supply temperature", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = KM200Result.class),
            @ApiResponse(code = 204, message = "no content")
    })
    @RequestMapping(value = "/actualsupttmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KM200Result> getActualSupplyTemperature() {
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
            String message = restCall.doGetRequest(SystemValues.SYSTMPSUPT1SET.getDescription());
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

    // PUT Methods

    @Override
    @ApiOperation(value = "Buderus change operation mode, switch between summer and winter mode (off = summer, forced = winter)", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = String.class),
            @ApiResponse(code = 204, message = "no content!")})
    @RequestMapping(value = "/chgheatop/{mode}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeHeatOpMode(@ApiParam(value = "operation mode", required = true) @PathVariable("mode") String mode) {
        String message = null;
        HttpStatus status = HttpStatus.OK;
        JSONObject jsonObj = new JSONObject();
        SuWiSwitchMode operationMode = SuWiSwitchMode.contains(mode);
        if (operationMode == null) {
            return new ResponseEntity<String>("Operation mode is not valid", HttpStatus.BAD_REQUEST);
        }
        jsonObj.put("value", operationMode.name().toLowerCase());
        try {
            message = restCall.doPostRequest(PushTopics.HEATCIRCUITHC1SUWISWITCHMODE.getDescription(), jsonObj.toJSONString());
            message = restCall.doPostRequest(PushTopics.HEATCIRCUITHC2SUWISWITCHMODE.getDescription(), jsonObj.toJSONString());
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<String>(message, status);
    }

    @Override
    @ApiOperation(value = "Buderus turn heating circuit on (1 = Heating Circuit 1, 2 = Heating Circuit 2)", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = String.class),
            @ApiResponse(code = 204, message = "no content!")})
    @RequestMapping(value = "/turnhcon/{hc}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> turnHCOn(@ApiParam(value = "turn heating circuit on", required = true) @PathVariable("hc") String hc) {
        String heatService = null;
        if(!StringUtils.isEmpty(hc)){
            Circuit circuit = Circuit.contains(hc);
            if(circuit != null){
                if(circuit.name().equals(Circuit.Heating_Circuit_1.name())){
                    heatService = PushTopics.HEATCIRCUITHC1ACTSWITCHPROG.getDescription();
                }
                else if (circuit.name().equals(Circuit.Heating_Circuit_2.name())){
                    heatService = PushTopics.HEATCIRCUITHC2ACTSWITCHPROG.getDescription();
                }
                if(heatService != null){
                    try {
                        return super.turnHeatOn(restCall, heatService);
                    } catch (Exception e) {
                        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
        }
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
    }

    @Override
    @ApiOperation(value = "Buderus turn heating circuit off (1 = Heating Circuit 1, 2 = Heating Circuit 2)", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = String.class),
            @ApiResponse(code = 204, message = "no content!")})
    @RequestMapping(value = "/turnhcoff/{hc}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> turnHCOff(@ApiParam(value = "turn heating circuit off", required = true) @HeaderParam("hc") String hc) {
        String heatService = null;
        String ecoService = null;
        if (!StringUtils.isEmpty(hc)) {
            Circuit circuit = Circuit.contains(hc);
            if (circuit != null) {
                if (circuit.name().equals(Circuit.Heating_Circuit_1.name())) {
                    heatService = PushTopics.HEATCIRCUITHC1ACTSWITCHPROG.getDescription();
                    ecoService = PushTopics.HEATCIRCUITHC1TMPLEVELECO.getDescription();
                } else if (circuit.name().equals(Circuit.Heating_Circuit_2.name())) {
                    heatService = PushTopics.HEATCIRCUITHC2ACTSWITCHPROG.getDescription();
                    ecoService = PushTopics.HEATCIRCUITHC2TMPLEVELECO.getDescription();
                }
                if (heatService != null && ecoService != null) {
                    try {
                        return super.turnHeatCuircuitOff(restCall, heatService, ecoService);
                    } catch (Exception e) {
                        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
        }
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
    }

    @Override
    @ApiOperation(value = "Buderus set room temperature for heating circuit 2 (tmp = Float value)", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = String.class),
            @ApiResponse(code = 204, message = "no content!")})
    @RequestMapping(value = "/chgroomtmp2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setRoomTmp2(
            @ApiParam(value = "room temperature", required = true, defaultValue = "20")
            @RequestParam(value = "tmp") String tmp) {
        String message = null;
        HttpStatus status = HttpStatus.OK;
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("value", Float.valueOf(tmp));
            // wo anders hin
            message = restCall.doPostRequest(PushTopics.HEATCIRCUITHC2TMPLEVELCOMF2.getDescription(), jsonObj.toJSONString());
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<String>(message, status);
    }

    @Override
    @ApiOperation(value = "Buderus set room temperature for heating circuit 1 (tmp = Float value)", response = Iterable.class, tags = "buderus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", response = String.class),
            @ApiResponse(code = 204, message = "no content!")})
    @RequestMapping(value = "/chgroomtmp1", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setRoomTmp1(
            @ApiParam(value = "room temperature", required = true, defaultValue = "20")
            @RequestParam(value = "tmp") String tmp) {
        String message = null;
        HttpStatus status = HttpStatus.OK;
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("value", Float.valueOf(tmp));
            // wo anders hin
            message = restCall.doPostRequest(PushTopics.HEATCIRCUITHC1TMPLEVELCOMF2.getDescription(), jsonObj.toJSONString());
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<String>(message, status);
    }
}
