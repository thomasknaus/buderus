/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.eclipse.smarthome.core.thing.ThingTypeUID
 */
package com.buderus.connection.config.util;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

public class KM200BindingConstants {
    public static final String BINDING_ID = "km200";
    public static final String CONFIG_DESCRIPTION_URI_CHANNEL = "channel-type:km200:config";
    public static final String CONFIG_DESCRIPTION_URI_THING = "thing-type:km200:config";
    public static final ThingTypeUID THING_TYPE_KMDEVICE = new ThingTypeUID("km200", "kmdevice");
    public static final ThingTypeUID THING_TYPE_GATEWAY = new ThingTypeUID("km200", "gateway");
    public static final ThingTypeUID THING_TYPE_DHW_CIRCUIT = new ThingTypeUID("km200", "dhwCircuit");
    public static final ThingTypeUID THING_TYPE_HEATING_CIRCUIT = new ThingTypeUID("km200", "heatingCircuit");
    public static final ThingTypeUID THING_TYPE_SOLAR_CIRCUIT = new ThingTypeUID("km200", "solarCircuit");
    public static final ThingTypeUID THING_TYPE_HEAT_SOURCE = new ThingTypeUID("km200", "heatSource");
    public static final ThingTypeUID THING_TYPE_SYSTEM = new ThingTypeUID("km200", "system");
    public static final ThingTypeUID THING_TYPE_SYSTEM_APPLIANCE = new ThingTypeUID("km200", "appliance");
    public static final ThingTypeUID THING_TYPE_SYSTEM_HOLIDAYMODES = new ThingTypeUID("km200", "holidayMode");
    public static final ThingTypeUID THING_TYPE_SYSTEM_SENSOR = new ThingTypeUID("km200", "sensor");
    public static final ThingTypeUID THING_TYPE_NOTIFICATION = new ThingTypeUID("km200", "notification");
    public static final ThingTypeUID THING_TYPE_SWITCH_PROGRAM = new ThingTypeUID("km200", "switchProgram");
    public static final String SWITCH_PROGRAM_REPLACEMENT = "__current__";
    public static final String SWITCH_PROGRAM_PATH_NAME = "switchPrograms";
    public static final String SWITCH_PROGRAM_CURRENT_PATH_NAME = "activeSwitchProgram";
    public static final String SWITCH_PROGRAM_POSITIVE = "activeSwitchProgramPos";
    public static final String SWITCH_PROGRAM_NEGATIVE = "activeSwitchProgramNeg";
}

