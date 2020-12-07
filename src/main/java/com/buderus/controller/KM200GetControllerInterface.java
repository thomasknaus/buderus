package com.buderus.controller;

import com.buderus.connection.config.KM200Result;
import org.springframework.http.ResponseEntity;

public interface KM200GetControllerInterface {

    /*
    * /system/appliance/actualPower
     */
    ResponseEntity<KM200Result> getActualPower();

    /*
    * Aktuelle Vorlauftemperatur --> Heizungsmischer AI Eingang
     */
    ResponseEntity<KM200Result> getActualSupplyTemperature();

    /*
    * Solltemperatur Vorlauf --> Heizungsmischer T Eingang
     */
    ResponseEntity<KM200Result> getDesiredSupplyTemperature();

    /*
    * Aktuell Außentemperatur --> Intelligente Raumsteuerung AI Eingang
     */
    ResponseEntity<KM200Result> getOutdoorTmp();

    /*
     * Aktuell Raumtemperatur Heizkreis 1
     */
    ResponseEntity<KM200Result> getActualRoomTmpHc1();

    /*
     * Aktuell Raumtemperatur Heizkreis 2
     */
    ResponseEntity<KM200Result> getActualRoomTmpHc2();
}
