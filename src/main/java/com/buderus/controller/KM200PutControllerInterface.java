package com.buderus.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface KM200PutControllerInterface {
    /*
    * set OperationMode
     */
    ResponseEntity<String> changeHeatOpMode(@ApiParam(value = "period to look in the past", required = true) @PathVariable("mode") String mode);

    /*
    * /heatingCircuits/hc1/suWiSwitchMode: OFF --> Heizungsmischer Q2 Ausgang
    * /heatingCircuits/hc2/suWiSwitchMode: OFF --> Heizungsmischer Q2 Ausgang
     */
    ResponseEntity<String> turnHeatOff();

    /*
    * /heatingCircuits/hc1/temporaryRoomSetpoint (floatValue): 0 = OFF  --> intelligente Raumsteuerung AQt Ausgang
    * /heatingCircuits/hc2/temporaryRoomSetpoint (floatValue): 0 = OFF --> intelligente Raumsteuerung AQt Ausgang
     */
    ResponseEntity<String> turnRoomTmpOff();

    /*
    * /heatingCircuits/hc1/temporaryRoomSetpoint (floatValue): 5 - 30  --> intelligente Raumsteuerung AQt Ausgang
    * /heatingCircuits/hc2/temporaryRoomSetpoint (floatValue): 5 - 30 --> intelligente Raumsteuerung AQt Ausgang
     */
    ResponseEntity<String> setRoomTmp(@ApiParam(value = "room temperature", required = true) @PathVariable("tmp") String tmp);

    /*
    * /heatingCircuits/hc1/suWiSwitchMode: FORCED --> Heizungsmischer Q1 Ausgang
    * /heatingCircuits/hc2/suWiSwitchMode: FORCED --> Heizungsmischer Q1 Ausgang
     */
    ResponseEntity<String> turnHeatOn();
}
