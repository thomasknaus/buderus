package com.buderus.connection.call.subscribe;

public enum Recordings implements KM200SubscribeValues{
    RECORD {
        @Override
        public String toString() { return "RECORD"; }
        @Override
        public String getDescription() {
            return "/recordings";
        }
    },
    RECORDSHEATCIRCUIT {
        @Override
        public String toString() { return "RECORDSHEATCIRCUIT"; }
        @Override
        public String getDescription() {
            return "/recordings/heatingCircuits";
        }
    },
    RECORDSHEATCIRCUITHC1 {
        @Override
        public String toString() { return "RECORDSHEATCIRCUITHC1"; }
        @Override
        public String getDescription() {
            return "/recordings/heatingCircuits/hc1";
        }
    },
    RECORDSHEATCIRCUITHC1ROOMTMP {
        @Override
        public String toString() { return "RECORDSHEATCIRCUITHC1ROOMTMP"; }
        @Override
        public String getDescription() {
            return "/recordings/heatingCircuits/hc1/roomtemperature";
        }
    },
    RECORDSHEATSRC {
        @Override
        public String toString() { return "RECORDSHEATSRC"; }
        @Override
        public String getDescription() {
            return "/recordings/heatSources";
        }
    },
    RECORDSHEATSRCACTCHPOW {
        @Override
        public String toString() { return "RECORDSHEATSRCACTCHPOW"; }
        @Override
        public String getDescription() {
            return "/recordings/heatSources/actualCHPower";
        }
    },
    RECORDSHEATSRCACTDHWPOW {
        @Override
        public String toString() { return "RECORDSHEATSRCACTDHWPOW"; }
        @Override
        public String getDescription() {
            return "/recordings/heatSources/actualDHWPower";
        }
    },
    RECORDSHEATSRCACTPOW {
        @Override
        public String toString() { return "RECORDSHEATSRCACTPOW"; }
        @Override
        public String getDescription() {
            return "/recordings/heatSources/actualPower";
        }
    },
    RECORDSHEATSRCHS1 {
        @Override
        public String toString() { return "RECORDSHEATSRCHS1"; }
        @Override
        public String getDescription() {
            return "/recordings/heatSources/hs1";
        }
    },
    RECORDSHEATSRCHS1ACTPOW {
        @Override
        public String toString() { return "RECORDSHEATSRCHS1ACTPOW"; }
        @Override
        public String getDescription() {
            return "/recordings/heatSources/hs1/actualPower";
        }
    },
    RECORDSYS {
        @Override
        public String toString() { return "RECORDSYS"; }
        @Override
        public String getDescription() {
            return "/recordings/system";
        }
    },
    RECORDSYSHEATSRC{
        @Override
        public String toString() { return "RECORDSYSHEATSRC"; }
        @Override
        public String getDescription() {
            return "/recordings/system/heatSources";
        }
    },
    RECORDSYSHEATSRCHS1 {
        @Override
        public String toString() { return "RECORDSYSHEATSRCHS1"; }
        @Override
        public String getDescription() {
            return "/recordings/system/heatSources/hs1";
        }
    },
    RECORDSYSHEATSRCHS1ACTPOW {
        @Override
        public String toString() { return "RECORDSYSHEATSRCHS1ACTPOW"; }
        @Override
        public String getDescription() {
            return "/recordings/system/heatSources/hs1/actualPower";
        }
    },
    RECORDSYSSENSOR {
        @Override
        public String toString() { return "RECORDSYSSENSOR"; }
        @Override
        public String getDescription() {
            return "/recordings/system/sensors";
        }
    },
    RECORDSYSSENSORTMP {
        @Override
        public String toString() { return "RECORDSYSSENSORTMP"; }
        @Override
        public String getDescription() {
            return "/recordings/system/sensors/temperatures";
        }
    },
    RECORDSYSSENSORTMPOUTT1 {
        @Override
        public String toString() { return "RECORDSYSSENSORTMPOUTT1"; }
        @Override
        public String getDescription() {
            return "/recordings/system/sensors/temperatures/outdoor_t1";
        }
    };

    public Recordings descriptionOf(String description){
        for(Recordings topic : Recordings.values()){
            if(topic.getDescription().equals(description)){
                return topic;
            }
        }
        return null;
    }
    public abstract String getDescription();
}
