package com.buderus.connection.call.subscribe;

public enum HeatCircuit2 implements KM200SubscribeValues {

    HEATHC2CURROOMSET {
        @Override
        public String toString() {
            return "HEATHC2CURROOMSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/currentRoomSetpoint";
        }
    },
    HEATHC2ACTSUPTMP {
        @Override
        public String toString() {
            return "HEATHC2CURROOMSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/actualSupplyTemperature";
        }
    },
    HEATHC2MANROOMSET {
        @Override
        public String toString() {
            return "HEATHC2MANROOMSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/manualRoomSetpoint";
        }
    },
    HEATHC2TEMPROOMSET {
        @Override
        public String toString() {
            return "HEATHC2TEMPROOMSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/temporaryRoomSetpoint";
        }
    },
    HEATHC2ROOMTMP {
        @Override
        public String toString() {
            return "HEATHC2ROOMTMP";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/roomtemperature";
        }
    },
    HEATHC2TIMENSET {
        @Override
        public String toString() {
            return "HEATHC2TIMENSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/timeToNextSetpoint";
        }
    },
    HEATHC2NSET {
        @Override
        public String toString() {
            return "HEATHC2NSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/nextSetpoint";
        }
    },
    HEATHC2SWITCHPRG {
        @Override
        public String toString() {
            return "HEATHC2SWITCHPRG";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/switchPrograms";
        }
    },
    HEATHC2TMPLEVEL {
        @Override
        public String toString() {
            return "HEATHC2TMPLEVEL";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/temperatureLevels";
        }
    },
    HEATHC2PUMPMODULA {
        @Override
        public String toString() {
            return "HEATHC2PUMPMODULA";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/pumpModulation";
        }
    },
    HEATHC2DESIGNTEMP {
        @Override
        public String toString() {
            return "HEATHC2DESIGNTEMP";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/designTemp";
        }
    },
    HEATHC2ROOMTEMPOFFSET {
        @Override
        public String toString() {
            return "HEATHC2ROOMTEMPOFFSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/roomTempOffset";
        }
    },
    HEATHC2HEATCURVEMAX {
        @Override
        public String toString() {
            return "HEATHC2HEATCURVEMAX";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/heatCurveMax";
        }
    },
    HEATHC2CONTROLTYPE {
        @Override
        public String toString() {
            return "HEATHC2CONTROLTYPE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/controlType";
        }
    },
    HEATHC2ROOMINFLUENCE {
        @Override
        public String toString() {
            return "HEATHC2ROOMINFLUENCE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/roomInfluence";
        }
    },
    HEATHC2CUROPMODEINFO {
        @Override
        public String toString() {
            return "HEATHC2CUROPMODEINFO";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/currentOpModeInfo";
        }
    },
    HEATHC2STATUS {
        @Override
        public String toString() {
            return "HEATHC2STATUS";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/status";
        }
    };

    public HeatCircuit2 descriptionOf(String description) {
        for (HeatCircuit2 topic : HeatCircuit2.values()) {
            if (topic.getDescription().equals(description)) {
                return topic;
            }
        }
        return null;
    }

    public abstract String getDescription();
}
