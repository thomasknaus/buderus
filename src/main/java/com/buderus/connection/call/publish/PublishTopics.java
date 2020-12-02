package com.buderus.connection.call.publish;

import com.buderus.connection.call.subscribe.HeatCircuit1;
import com.buderus.connection.call.subscribe.KM200SubscribeValues;

public enum PublishTopics implements KM200SubscribeValues {

    HEATCIRCUITHC1OPERATIONMODE {
        // String Value OperationModeHC
        @Override
        public String toString() {
            return "HEATCIRCUITHC1OPERATIONMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/operationMode";
        }
    },
    HEATCIRCUITHC1TEMPOROOMSET {
        // Float Value
        @Override
        public String toString() {
            return "HEATCIRCUITHC1TEMPOROOMSET";
        }

        @Override
        public String getDescription() {
            return /*"/km200/status*/"/heatingCircuits/hc1/temporaryRoomSetpoint";
        }
    },
    HEATCIRCUITHC1ACTSWITCHPROG {
        // String Value "A", "B"
        @Override
        public String toString() {
            return "HEATCIRCUITHC1ACTSWITCHPROG";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/activeSwitchProgram";
        }
    },
    HEATCIRCUITHC1SUWITHREESHOLD {
        // Float Value
        @Override
        public String toString() {
            return "HEATCIRCUITHC1SUWITHREESHOLD";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/suWiThreshold";
        }
    },
    HEATCIRCUITHC1SUWISWITCHMODE {
        // String Value SuWiSwitchMode
        @Override
        public String toString() {
            return "HEATCIRCUITHC1SUWISWITCHMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/suWiSwitchMode";
        }
    },
    HEATCIRCUITHC1TMPROOMSET {
        // String value HCMode.class
        @Override
        public String toString() {
            return "HEATCIRCUITHC1TMPROOMSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc1/temperatureRoomSetpoint";
        }
    },
    HEATCIRCUITHC2OPERATIONMODE {
        // String Value OperationModeHC
        @Override
        public String toString() {
            return "HEATCIRCUITHC2OPERATIONMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/operationMode";
        }
    },
    HEATCIRCUITHC2TEMPOROOMSET {
        // Float Value
        @Override
        public String toString() {
            return "HEATCIRCUITHC2TEMPOROOMSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/temporaryRoomSetpoint";
        }
    },
    HEATCIRCUITHC2ACTSWITCHPROG {
        // String Value "A", "B"
        @Override
        public String toString() {
            return "HEATCIRCUITHC2ACTSWITCHPROG";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/activeSwitchProgram";
        }
    },
    HEATCIRCUITHC2SUWITHREESHOLD {
        // Float Value
        @Override
        public String toString() {
            return "HEATCIRCUITHC2SUWITHREESHOLD";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/suWiThreshold";
        }
    },
    HEATCIRCUITHC2TMPROOMSET {
        // String value HCMode.class
        @Override
        public String toString() {
            return "HEATCIRCUITHC2TMPROOMSET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/temperatureRoomSetpoint";
        }
    },
    HEATCIRCUITHC2SUWISWITCHMODE {
        // String Value SuWiSwitchMode
        @Override
        public String toString() {
            return "HEATCIRCUITHC2SUWISWITCHMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/heatingCircuits/hc2/suWiSwitchMode";
        }
    },
    DHWCURCUITDHW1CHARGE {
        // String Value "start", "stop"
        @Override
        public String toString() {
            return "DHWCURCUITDHW1CHARGE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/dhwCircuits/dhw1/charge";
        }
    },
    DHWCURCUITDHW1CHARGEDURATION {
        // Float Value
        @Override
        public String toString() {
            return "DHWCURCUITDHW1CHARGEDURATION";
        }

        @Override
        public String getDescription() {
            return "/km200/status/dhwCircuits/dhw1/chargeDuration";
        }
    },
    DHWCURCUITDHW1OPERATIONMODE {
        // String Value OperationModeDHW.class
        @Override
        public String toString() {
            return "DHWCURCUITDHW1OPERATIONMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/dhwCircuits/dhw1/operationMode";
        }
    },
    DHWCURCUITDHW1SINGLECHARGESET {
        // Float Value
        @Override
        public String toString() {
            return "DHWCURCUITDHW1SINGLECHARGESET";
        }

        @Override
        public String getDescription() {
            return "/km200/status/dhwCircuits/dhw1/singleChargeSetpoint";
        }
    },
    DHWCURCUITDHW1TMPLEVELHIGH {
        // Float Value
        @Override
        public String toString() {
            return "DHWCURCUITDHW1TMPLEVELHIGH";
        }

        @Override
        public String getDescription() {
            return "/km200/status/dhwCircuits/dhw1/temperatureLevels/high";
        }
    },
    SYSMINOUTTEMP {
        // Float Value
        @Override
        public String toString() {
            return "SYSMINOUTTEMP";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/minOutdoorTemp";
        }
    },
    SYSHOLIDAYMODEHM1ASSIGN {
        // Array Data HolidayModeCircuit.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM1ASSIGN";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm1/assignedTo";
        }
    },
    SYSHOLIDAYMODEHM1DHWMODE {
        // String value DHWMode.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM1DHWMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm1/dhwMode";
        }
    },
    SYSHOLIDAYMODEHM1HCMODE {
        // String value HCMode.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM1HCMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm1/hcMode";
        }
    },
    SYSHOLIDAYMODEHM2ASSIGN {
        // Array Data HolidayModeCircuit.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM2ASSIGN";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm2/assignedTo";
        }
    },
    SYSHOLIDAYMODEHM2DHWMODE {
        // String value DHWMode.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM2DHWMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm2/dhwMode";
        }
    },
    SYSHOLIDAYMODEHM2HCMODE {
        // String value HCMode.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM2HCMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm2/hcMode";
        }
    },
    SYSHOLIDAYMODEHM3ASSIGN {
        // Array Data HolidayModeCircuit.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM3ASSIGN";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm3/assignedTo";
        }
    },
    SYSHOLIDAYMODEHM3DHWMODE {
        // String value DHWMode.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM3DHWMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm3/dhwMode";
        }
    },
    SYSHOLIDAYMODEHM3HCMODE {
        // String value HCMode.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM3HCMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm3/hcMode";
        }
    },
    SYSHOLIDAYMODEHM4ASSIGN {
        // Array Data HolidayModeCircuit.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM4ASSIGN";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm4/assignedTo";
        }
    },
    SYSHOLIDAYMODEHM4DHWMODE {
        // String value DHWMode.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM4DHWMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm4/dhwMode";
        }
    },
    SYSHOLIDAYMODEHM4HCMODE {
        // String value HCMode.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM4HCMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm4/hcMode";
        }
    },
    SYSHOLIDAYMODEHM5ASSIGN {
        // Array Data HolidayModeCircuit.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM5ASSIGN";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm5/assignedTo";
        }
    },
    SYSHOLIDAYMODEHM5DHWMODE {
        // String value DHWMode.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM5DHWMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm5/dhwMode";
        }
    },
    SYSHOLIDAYMODEHM5HCMODE {
        // String value HCMode.class
        @Override
        public String toString() {
            return "SYSHOLIDAYMODEHM5HCMODE";
        }

        @Override
        public String getDescription() {
            return "/km200/status/system/holidayModes/hm5/hcMode";
        }
    };





    public PublishTopics descriptionOf(String description) {
        for (PublishTopics topic : PublishTopics.values()) {
            if (topic.getDescription().equals(description)) {
                return topic;
            }
        }
        return null;
    }

    public abstract String getDescription();
}
