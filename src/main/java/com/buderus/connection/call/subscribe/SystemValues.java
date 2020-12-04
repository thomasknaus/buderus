package com.buderus.connection.call.subscribe;

public enum SystemValues implements KM200SubscribeValues{
    CONNECTED {
        @Override
        public String toString() { return "CONNECTED"; }
        @Override
        public String getDescription() {
            return "km200/connected";
        }
    },
    SYSINFO {
        @Override
        public String toString() { return "SYSINFO"; }
        @Override
        public String getDescription() {
            return "km200/status/system/info";
        }
    },
    SYSMINOUTTMP {
        @Override
        public String toString() { return "SYSMINOUTTMP"; }
        @Override
        public String getDescription() {
            return "km200/status/system/minOutdoorTemp";
        }
    },
    SYSSENSORS {
        @Override
        public String toString() { return "SYSSENSORS"; }
        @Override
        public String getDescription() {
            return "km200/status/system/sensors";
        }
    },
    SYSTMP {
        @Override
        public String toString() { return "SYSTMP"; }
        @Override
        public String getDescription() {
            return "km200/status/system/sensors/temperatures";
        }
    },
    SYSTMPCHIMNEY {
        @Override
        public String toString() { return "SYSTMPCHIMNEY"; }
        @Override
        public String getDescription() {
            return "km200/status/system/sensors/temperatures/chimney";
        }
    },
    /**
     * Heißwassertemperatur T1 {@link SystemValues#SYSTMPHOTWATT1}.
     */
    SYSTMPHOTWATT1 {
        @Override
        public String toString() { return "SYSTMPHOTWATT1"; }
        @Override
        public String getDescription() {
            return "km200/status/system/sensors/temperatures/hotWater_t1";
        }
    },
    /**
     * Heißwassertemperatur T2 {@link SystemValues#SYSTMPHOTWATT2}.
     */
    SYSTMPHOTWATT2 {
        @Override
        public String toString() { return "SYSTMPHOTWATT2"; }
        @Override
        public String getDescription() {
            return "km200/status/system/sensors/temperatures/hotWater_t2";
        }
    },
    /**
     * Außentemperatur {@link SystemValues#SYSTMPOUTT1}.
     */
    SYSTMPOUTT1 {
        @Override
        public String toString() { return "SYSTMPOUTT1"; }
        @Override
        public String getDescription() {
            return /*"km200/status*/"/system/sensors/temperatures/outdoor_t1";
        }
    },
    /**
     * Rücklauftemperatur {@link SystemValues#SYSTMPRET}.
     */
    SYSTMPRET {
        @Override
        public String toString() { return "SYSTMPRET"; }
        @Override
        public String getDescription() {
            return "km200/status/system/sensors/temperatures/return";
        }
    },
    /**
     * Solltemperatur Vorlauf {@link SystemValues#SYSTMPSUPT1}.
     */
    SYSTMPSUPT1 {
        @Override
        public String toString() { return "SYSTMPSUPT1"; }
        @Override
        public String getDescription() { return /*"km200/status*/"/system/sensors/temperatures/supply_t1"; }
    },
    SYSTMPSUPT1SET {
        @Override
        public String toString() { return "SYSTMPSUPT1SET"; }
        @Override
        public String getDescription() { return /*"km200/status*/"/system/sensors/temperatures/supply_t1_setpoint"; }
    },
    SYSTMPSWITCH {
        @Override
        public String toString() { return "SYSTMPSWITCH"; }
        @Override
        public String getDescription() { return "km200/status/system/sensors/temperatures/switch"; }
    },
    SYSAPP {
        @Override
        public String toString() { return "SYSAPP"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance"; }
    },
    SYSAPPACTPOW {
        @Override
        public String toString() { return "SYSAPPACTPOW"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance/actualPower"; }
    },

    /**
     * Aktuelle Vorlauftemperatur {@link SystemValues#SYSAPPACTSUPTMP}.
     */
    SYSAPPACTSUPTMP {
        @Override
        public String toString() { return "SYSAPPACTSUPTMP"; }
        @Override
        public String getDescription() { return /*"km200/status*/"/system/appliance/actualSupplyTemperature"; }
    },
    SYSAPPCHIMNEYSWEEP {
        @Override
        public String toString() { return "SYSAPPCHIMNEYSWEEP"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance/ChimneySweeper"; }
    },
    SYSAPPCHPUMPMODULA {
        @Override
        public String toString() { return "SYSAPPCHPUMPMODULA"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance/CHpumpModulation"; }
    },
    /**
     * Brenner Status Flame an {@link SystemValues#SYSAPPFLAMECUR}.
     */
    SYSAPPFLAMECUR {
        @Override
        public String toString() { return "SYSAPPFLAMECUR"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance/flameCurrent"; }
    },
    SYSAPPGASAIRPRESS {
        @Override
        public String toString() { return "SYSAPPGASAIRPRESS"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance/gasAirPressure"; }
    },
    SYSAPPNOMBURNER {
        @Override
        public String toString() { return "SYSAPPNOMBURNER"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance/nominalBurnerLoad"; }
    },
    SYSAPPNUMOFSTART {
        @Override
        public String toString() { return "SYSAPPNUMOFSTART"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance/numberOfStarts"; }
    },
    SYSAPPPOWSET {
        @Override
        public String toString() { return "SYSAPPPOWSET"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance/powerSetpoint"; }
    },
    SYSAPPSYSPRESS {
        @Override
        public String toString() { return "SYSAPPSYSPRESS"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance/systemPressure"; }
    },
    SYSAPPWORKTIME {
        @Override
        public String toString() { return "SYSAPPWORKTIME"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance/workingTime"; }
    },
    SYSAPPWORKTIMECENTRHEAT {
        @Override
        public String toString() { return "SYSAPPWORKTIMECENTRHEAT"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance/workingTime/centralHeating"; }
    },
    SYSAPPWORKTIMESECBURN {
        @Override
        public String toString() { return "SYSAPPWORKTIMESECBURN"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance/workingTime/secondBurner"; }
    },
    SYSAPPWORKTIMETOTSYS {
        @Override
        public String toString() { return "SYSAPPWORKTIMETOTSYS"; }
        @Override
        public String getDescription() { return "km200/status/system/appliance/workingTime/totalSystem"; }
    },
    SYSBRAND {
        @Override
        public String toString() { return "SYSBRAND"; }
        @Override
        public String getDescription() { return "km200/status/system/brand"; }
    },
    SYSBUS {
        @Override
        public String toString() { return "SYSBUS"; }
        @Override
        public String getDescription() { return "km200/status/system/bus"; }
    },
    SYSHEALTHSTATUS {
        @Override
        public String toString() { return "SYSHEALTHSTATUS"; }
        @Override
        public String getDescription() { return "km200/status/system/healthStatus"; }
    },
    SYSHEATSOURCE {
        @Override
        public String toString() { return "SYSHEATSOURCE"; }
        @Override
        public String getDescription() { return "km200/status/system/heatSources"; }
    },
    SYSHEATSOURCEHS1 {
        @Override
        public String toString() { return "SYSHEATSOURCEHS1"; }
        @Override
        public String getDescription() { return "km200/status/system/heatSources/hs1"; }
    },
    SYSHEATSOURCEHS1ACTMODULA {
        @Override
        public String toString() { return "SYSHEATSOURCEHS1ACTMODULA"; }
        @Override
        public String getDescription() { return "km200/status/system/heatSources/hs1/actualModulation"; }
    },
    SYSHEATSOURCEHS1ACTPOW {
        @Override
        public String toString() { return "SYSHEATSOURCEHS1ACTPOW"; }
        @Override
        public String getDescription() { return "km200/status/system/heatSources/hs1/actualPower"; }
    },
    SYSHEATSOURCEHS1ENGRESVOIR {
        @Override
        public String toString() { return "SYSHEATSOURCEHS1ENGRESVOIR"; }
        @Override
        public String getDescription() { return "km200/status/system/heatSources/hs1/energyReservoir"; }
    },
    SYSHEATSOURCEHS1FUEL {
        @Override
        public String toString() { return "SYSHEATSOURCEHS1FUEL"; }
        @Override
        public String getDescription() { return "km200/status/system/heatSources/hs1/fuel"; }
    },
    SYSHEATSOURCEHS1FUELCALVAL {
        @Override
        public String toString() { return "SYSHEATSOURCEHS1FUELCALVAL"; }
        @Override
        public String getDescription() { return "km200/status/system/heatSources/hs1/fuel/caloricValue"; }
    },
    SYSHEATSOURCEHS1FUELDENSITY {
        @Override
        public String toString() { return "SYSHEATSOURCEHS1FUELDENSITY"; }
        @Override
        public String getDescription() { return "km200/status/system/heatSources/hs1/fuel/density"; }
    },
    SYSHEATSOURCEHS1FUELCSMPTCORFACT {
        @Override
        public String toString() { return "SYSHEATSOURCEHS1FUELCSMPTCORFACT"; }
        @Override
        public String getDescription() { return "km200/status/system/heatSources/hs1/fuelConsmptCorrFactor"; }
    },
    SYSHEATSOURCEHS1NOMFUELCONS {
        @Override
        public String toString() { return "SYSHEATSOURCEHS1NOMFUELCONS"; }
        @Override
        public String getDescription() { return "km200/status/system/heatSources/hs1/nominalFuelConsumption"; }
    },
    SYSHEATSOURCEHS1RESALERT {
        @Override
        public String toString() { return "SYSHEATSOURCEHS1RESALERT"; }
        @Override
        public String getDescription() { return "km200/status/system/heatSources/hs1/reservoirAlert"; }
    },
    NOTIFICATIONS {
        @Override
        public String toString() { return "NOTIFICATIONS"; }
        @Override
        public String getDescription() { return "km200/status/notifications'"; }
    };

    public SystemValues descriptionOf(String description){
        for(SystemValues topic : SystemValues.values()){
            if(topic.getDescription().equals(description)){
                return topic;
            }
        }
        return null;
    }

    public abstract String getDescription();

}
