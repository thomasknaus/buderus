package com.buderus.connection.call.subscribe;

public enum WaterCircuit1 implements KM200SubscribeValues {
    DHW1CIRACTTMP {
        @Override
        public String toString() {
            return "DHW1CIRACTTMP";
        }

        @Override
        public String getDescription() {
            return "km200/status/dhwCircuits/dhw1/actualTemp";
        }
    },
    DHW1CIRCPSTARTPH {
        @Override
        public String toString() {
            return "DHW1CIRCPSTARTPH";
        }

        @Override
        public String getDescription() {
            return "km200/status/dhwCircuits/dhw1/cpStartph";
        }
    },
    DHW1CIRCURSET {
        @Override
        public String toString() {
            return "DHW1CIRCURSET";
        }

        @Override
        public String getDescription() {
            return "km200/status/dhwCircuits/dhw1/currentSetpoint";
        }
    },
    DHW1CIRSTATUS {
        @Override
        public String toString() {
            return "DHW1CIRSTATUS";
        }

        @Override
        public String getDescription() {
            return "km200/status/dhwCircuits/dhw1/status";
        }
    },
    DHW1CIRSWITCHPRG {
        @Override
        public String toString() {
            return "DHW1CIRSWITCHPRG";
        }

        @Override
        public String getDescription() {
            return "km200/status/dhwCircuits/dhw1/switchPrograms";
        }
    },
    DHW1CIRTDMODE {
        @Override
        public String toString() {
            return "DHW1CIRTDMODE";
        }

        @Override
        public String getDescription() {
            return "km200/status/dhwCircuits/dhw1/tdMode";
        }
    },
    DHW1CIRTDSETPOINT {
        @Override
        public String toString() {
            return "DHW1CIRTDSETPOINT";
        }

        @Override
        public String getDescription() {
            return "km200/status/dhwCircuits/dhw1/tdsetPoint";
        }
    },
    DHW1CIRTMPLEVEL {
        @Override
        public String toString() {
            return "DHW1CIRTMPLEVEL";
        }

        @Override
        public String getDescription() {
            return "km200/status/dhwCircuits/dhw1/temperatureLevels";
        }
    },
    DHW1CIRTMPLEVELOFF {
        @Override
        public String toString() {
            return "DHW1CIRTMPLEVELOFF";
        }

        @Override
        public String getDescription() {
            return "km200/status/dhwCircuits/dhw1/temperatureLevels/off";
        }
    },
    DHW1CIRWATERFLOW {
        @Override
        public String toString() {
            return "DHW1CIRWATERFLOW";
        }

        @Override
        public String getDescription() {
            return "km200/status/dhwCircuits/dhw1/waterFlow";
        }
    },
    DHW1CIRWORKIME {
        @Override
        public String toString() {
            return "DHW1CIRWORKIME";
        }

        @Override
        public String getDescription() {
            return "km200/status/dhwCircuits/dhw1/workingTime";
        }
    };

    public WaterCircuit1 descriptionOf(String description) {
        for (WaterCircuit1 topic : WaterCircuit1.values()) {
            if (topic.getDescription().equals(description)) {
                return topic;
            }
        }
        return null;
    }

    public abstract String getDescription();
}
