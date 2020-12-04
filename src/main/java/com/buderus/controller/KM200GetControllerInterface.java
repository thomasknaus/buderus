package com.buderus.controller;

import com.buderus.connection.config.KM200Result;
import org.springframework.http.ResponseEntity;

public interface KM200GetControllerInterface {

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
}
