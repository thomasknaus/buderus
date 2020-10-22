/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.eclipse.smarthome.core.thing.ThingTypeUID
 */
package com.buderus.connection.config.util;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum KM200ThingType {
    GATEWAY("/gateway", KM200BindingConstants.THING_TYPE_GATEWAY){

        @Override
        public List<String> asBridgeProperties() {
            ArrayList<String> asProperties = new ArrayList<String>();
            asProperties.add("versionFirmware");
            asProperties.add("instAccess");
            asProperties.add("versionHardware");
            asProperties.add("uuid");
            asProperties.add("instWriteAccess");
            asProperties.add("openIPAccess");
            return asProperties;
        }
    }
    ,
    DHWCIRCUIT("/dhwCircuits", KM200BindingConstants.THING_TYPE_DHW_CIRCUIT){

        @Override
        public List<String> ignoreSubService() {
            ArrayList<String> subServices = new ArrayList<String>();
            subServices.add("switchPrograms");
            return subServices;
        }

        @Override
        public String getActiveCheckSubPath() {
            return "status";
        }
    }
    ,
    HEATINGCIRCUIT("/heatingCircuits", KM200BindingConstants.THING_TYPE_HEATING_CIRCUIT){

        @Override
        public List<String> ignoreSubService() {
            ArrayList<String> subServices = new ArrayList<String>();
            subServices.add("switchPrograms");
            return subServices;
        }

        @Override
        public String getActiveCheckSubPath() {
            return "status";
        }
    }
    ,
    HEATSOURCE("/heatSources", KM200BindingConstants.THING_TYPE_HEAT_SOURCE),
    SOLARCIRCUIT("/solarCircuits", KM200BindingConstants.THING_TYPE_SOLAR_CIRCUIT){

        @Override
        public String getActiveCheckSubPath() {
            return "status";
        }
    }
    ,
    APPLIANCE("/system/appliance", KM200BindingConstants.THING_TYPE_SYSTEM_APPLIANCE),
    HOLIDAYMODES("/system/holidayModes", KM200BindingConstants.THING_TYPE_SYSTEM_HOLIDAYMODES),
    NOTIFICATIONS("/notifications", KM200BindingConstants.THING_TYPE_NOTIFICATION),
    SENSOR("/system/sensors", KM200BindingConstants.THING_TYPE_SYSTEM_SENSOR),
    SYSTEM("/system", KM200BindingConstants.THING_TYPE_SYSTEM){

        @Override
        public List<String> ignoreSubService() {
            ArrayList<String> subServices = new ArrayList<String>();
            subServices.add("sensors");
            subServices.add("appliance");
            subServices.add("holidayModes");
            return subServices;
        }

        @Override
        public List<String> asBridgeProperties() {
            ArrayList<String> asProperties = new ArrayList<String>();
            asProperties.add("bus");
            asProperties.add("systemType");
            asProperties.add("brand");
            asProperties.add("info");
            return asProperties;
        }
    }
    ,
    SWITCHPROGRAM("", KM200BindingConstants.THING_TYPE_SWITCH_PROGRAM);

    public final String rootPath;
    public final ThingTypeUID thingTypeUID;

    private KM200ThingType(String rootPath, ThingTypeUID thingTypeUID) {
        this.rootPath = rootPath;
        this.thingTypeUID = thingTypeUID;
    }

    public String getRootPath() {
        return this.rootPath;
    }

    public ThingTypeUID getThingTypeUID() {
        return this.thingTypeUID;
    }

    public List<String> ignoreSubService() {
        return Collections.emptyList();
    }

    public String getActiveCheckSubPath() {
        return null;
    }

    public List<String> asBridgeProperties() {
        return Collections.emptyList();
    }

    /* synthetic */ KM200ThingType(String string, int n, String string2, ThingTypeUID thingTypeUID, KM200ThingType kM200ThingType) {
        this(string2, thingTypeUID);
    }
}

