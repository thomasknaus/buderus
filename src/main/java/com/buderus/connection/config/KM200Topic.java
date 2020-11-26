package com.buderus.connection.config;

public enum KM200Topic {
    CONNECTED {
        @Override
        public String toString() {
            return "CONNECTED";
        }
        @Override
        public String getDescription() {
            return "km200/connected";
        }
    },
    CHIMNEY {
        @Override
        public String toString() {
            return "CHIMNEY";
        }
        @Override
        public String getDescription() {
            return "km200/status/system/sensors/temperatures/chimney";
        }
    },
    TMPOUTDOOR {
        @Override
        public String toString() {
            return "TMPOUTDOOR";
        }
        @Override
        public String getDescription() {
            return "km200/status/system/sensors/temperatures/outdoor_t1";
        }
    },
    ACTUALTMP {
        @Override
        public String toString() {
            return "ACTUALTMP";
        }
        @Override
        public String getDescription() {
            return "km200/status/dhwCircuits/dhw1/actualTemp";
        }
    },
    WORKTIME {
        @Override
        public String toString() {
            return "WORKTIME";
        }
        @Override
        public String getDescription() {
            return "km200/status/dhwCircuits/dhw1/workingTime";
        }
    },
    ACTSUPPLYTMP {
        @Override
        public String toString() {
            return "ACTSUPPLYTMP";
        }
        @Override
        public String getDescription() {
            return "km200/status/heatSources/actualSupplyTemperature";
        }
    },
    ROOMTMP {
        @Override
        public String toString() {
            return "ROOMTMP";
        }
        @Override
        public String getDescription() {
            return "km200/status/heatingCircuits/hc1/roomtemperature";
        }
    },
    SUPPLYSETPOINT {
        @Override
        public String toString() {
            return "SUPPLYSETPOINT";
        }
        @Override
        public String getDescription() {
            return "km200/status/system/sensors/temperatures/supply_t1_setpoint";
        }
    };

    private KM200Topic() {
    }

    public static KM200Topic descriptionOf(String description){
        for(KM200Topic topic : KM200Topic.values()){
            if(topic.getDescription().equals(description)){
                return topic;
            }
        }
        return null;
    }
    public abstract String getDescription();

    /* synthetic */ KM200Topic(String string, int n, KM200Topic kM200ServiceTypes) {
        this();
    }
}
