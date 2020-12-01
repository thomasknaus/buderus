package com.buderus.connection.call.subscribe;

public enum HeatCircuit1 implements KM200SubscribeValues{

    HEATHC1CURROOMSET {
        @Override
        public String toString() {
            return "HEATHC1CURROOMSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/currentRoomSetpoint";
        }
    },
    HEATHC1ACTSUPTMP {
        @Override
        public String toString() {
            return "HEATHC1CURROOMSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/actualSupplyTemperature";
        }
    },
    HEATHC1MANROOMSET {
        @Override
        public String toString() {
            return "HEATHC1MANROOMSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/manualRoomSetpoint";
        }
    },
    HEATHC1TEMPROOMSET {
        @Override
        public String toString() {
            return "HEATHC1TEMPROOMSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/temporaryRoomSetpoint";
        }
    },
    HEATHC1ROOMTMP {
        @Override
        public String toString() {
            return "HEATHC1ROOMTMP";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/roomtemperature";
        }
    },
    HEATHC1TIMENSET {
        @Override
        public String toString() {
            return "HEATHC1TIMENSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/timeToNextSetpoint";
        }
    },
    HEATHC1NSET {
        @Override
        public String toString() {
            return "HEATHC1NSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/nextSetpoint";
        }
    },
    HEATHC1SWITCHPRG {
        @Override
        public String toString() {
            return "HEATHC1SWITCHPRG";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/switchPrograms";
        }
    },
    HEATHC1TMPLEVEL {
        @Override
        public String toString() {
            return "HEATHC1TMPLEVEL";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/temperatureLevels";
        }
    },
    HEATHC1PUMPMODULA {
        @Override
        public String toString() {
            return "HEATHC1PUMPMODULA";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/pumpModulation";
        }
    },
    HEATHC1DESIGNTEMP {
        @Override
        public String toString() {
            return "HEATHC1DESIGNTEMP";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/designTemp";
        }
    },
    HEATHC1ROOMTEMPOFFSET {
        @Override
        public String toString() {
            return "HEATHC1ROOMTEMPOFFSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/roomTempOffset";
        }
    },
    HEATHC1HEATCURVEMAX {
        @Override
        public String toString() {
            return "HEATHC1HEATCURVEMAX";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/heatCurveMax";
        }
    },
    HEATHC1CONTROLTYPE {
        @Override
        public String toString() {
            return "HEATHC1CONTROLTYPE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/controlType";
        }
    },
    HEATHC1ROOMINFLUENCE {
        @Override
        public String toString() {
            return "HEATHC1ROOMINFLUENCE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/roomInfluence";
        }
    },
    HEATHC1CUROPMODEINFO {
        @Override
        public String toString() {
            return "HEATHC1CUROPMODEINFO";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/currentOpModeInfo";
        }
    },
    HEATHC1STATUS {
        @Override
        public String toString() {
            return "HEATHC1STATUS";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/status";
        }
    };

    public HeatCircuit1 descriptionOf(String description) {
        for (HeatCircuit1 topic : HeatCircuit1.values()) {
            if (topic.getDescription().equals(description)) {
                return topic;
            }
        }
        return null;
    }

    public abstract String getDescription();
}
