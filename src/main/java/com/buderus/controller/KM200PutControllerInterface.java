package com.buderus.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface KM200PutControllerInterface {
    /*
    * set OperationMode
     */
    ResponseEntity<String> changeHeatOpMode(@ApiParam(value = "period to look in the past", required = true) @PathVariable("mode") String mode);

    /*
    * /heatingCircuits/hc1/suWiSwitchMode: ON or OFF --> Heizungsfreigabe Klimacontroller 1 von Q1
     */
    ResponseEntity<Boolean> turnHCOn(@ApiParam(value = "turn heating circuit on", required = true) @PathVariable("hc") String hc);

    /*
     * /heatingCircuits/hc2/suWiSwitchMode: ON or OFF --> Heizungsfreigabe Klimacontroller 2 von Q1
     */
    ResponseEntity<Boolean> turnHCOff(@ApiParam(value = "turn heating circuit off", required = true) @PathVariable("hc") String hc);

    /*
    * /heatingCircuits/hc1/temporaryRoomSetpoint (floatValue): 5 - 30  --> intelligente Raumsteuerung 1 AQt Ausgang
     */
    ResponseEntity<String> setRoomTmp1(@ApiParam(value = "room temperature", required = true) @RequestParam("tmp") String tmp);

    /*
     * /heatingCircuits/hc2/temporaryRoomSetpoint (floatValue): 5 - 30 --> intelligente Raumsteuerung 2 AQt Ausgang
     */
    ResponseEntity<String> setRoomTmp2(@ApiParam(value = "room temperature", required = true) @RequestParam("tmp") String tmp);

}
