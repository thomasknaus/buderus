package com.buderus.connection.call.publish;

public enum KM200PublishTopic {
    HEATHC1OPERATION {
        @Override
        public String toString() { return "HEATHC1OPERATION"; }

        @Override
        public String getDescription() {
            return "/heatingCircuits/hc1/operationMode";
        }
    },
    HEATHC1TMPRSET {
        @Override
        public String toString() { return "HEATHC1TMPRSET"; }

        @Override
        public String getDescription() {
            return "/heatingCircuits/hc1/temporaryRoomSetpoint";
        }
    },
    HEATHC1ACTSWPRG {
        @Override
        public String toString() { return "HEATHC1ACTSWPRG"; }

        @Override
        public String getDescription() {
            return "/heatingCircuits/hc1/activeSwitchProgram";
        }
    },
    HEATHC1SUWITHR {
        @Override
        public String toString() { return "HEATHC1SUWITHR"; }

        @Override
        public String getDescription() {
            return "/heatingCircuits/hc1/suWiThreshold";
        }
    },
    HEATHC1SUWIMOD {
        @Override
        public String toString() { return "HEATHC1SUWIMOD"; }

        @Override
        public String getDescription() {
            return "/heatingCircuits/hc1/suWiSwitchMode";
        }
    },
    HEATHC2OPERATION {
        @Override
        public String toString() { return "HEATHC2OPERATION"; }

        @Override
        public String getDescription() {
            return "/heatingCircuits/hc2/operationMode";
        }
    },
    HEATHC2TMPRSET {
        @Override
        public String toString() { return "HEATHC2TMPRSET"; }

        @Override
        public String getDescription() {
            return "/heatingCircuits/hc2/temporaryRoomSetpoint";
        }
    },
    HEATHC2ACTSWPRG {
        @Override
        public String toString() { return "HEATHC2ACTSWPRG"; }

        @Override
        public String getDescription() {
            return "/heatingCircuits/hc2/activeSwitchProgram";
        }
    },
    HEATHC2SUWITHR {
        @Override
        public String toString() { return "HEATHC1SUWITHR"; }

        @Override
        public String getDescription() {
            return "/heatingCircuits/hc1/suWiThreshold";
        }
    },
    HEATHC2SUWIMOD {
        @Override
        public String toString() { return "HEATHC2SUWIMOD"; }

        @Override
        public String getDescription() {
            return "/heatingCircuits/hc2/suWiSwitchMode";
        }
    },
    DHW1CHARGE {
        @Override
        public String toString() { return "DHW1CHARGE"; }

        @Override
        public String getDescription() {
            return "/dhwCircuits/dhw1/charge";
        }
    },
    DHW1CHARGEDUR {
        @Override
        public String toString() { return "DHW1CHARGEDUR"; }

        @Override
        public String getDescription() {
            return "/dhwCircuits/dhw1/chargeDuration";
        }
    },
    DHW1OPERATION {
        @Override
        public String toString() { return "DHW1OPERATION"; }

        @Override
        public String getDescription() {
            return "/dhwCircuits/dhw1/operationMode";
        }
    },
    DHW1SINGLECHRGSET {
        @Override
        public String toString() { return "DHW1SINGLECHRGSET"; }

        @Override
        public String getDescription() {
            return "/dhwCircuits/dhw1/singleChargeSetpoint";
        }
    },
    DHW1TMPLEVELHIGH {
        @Override
        public String toString() { return "DHW1TMPLEVELHIGH"; }

        @Override
        public String getDescription() {
            return "/dhwCircuits/dhw1/temperatureLevels/high";
        }
    },
    SYSMINOUTTMP {
        @Override
        public String toString() { return "SYSMINOUTTMP"; }

        @Override
        public String getDescription() { return "/system/minOutdoorTemp"; }
    },
    SYSHOLMODHM1ASSIGN {
        @Override
        public String toString() { return "SYSHOLMODHM1ASSIGN"; }

        @Override
        public String getDescription() { return "/system/holidayModes/hm1/assignedTo"; }
    },
    SYSHOLMODHM1DHW {
        @Override
        public String toString() { return "SYSHOLMODHM1DHW"; }

        @Override
        public String getDescription() { return "/system/holidayModes/hm1/dhwMode"; }
    },
    SYSHOLMODHM1HCMOD {
        @Override
        public String toString() { return "SYSHOLMODHM1HCMOD"; }

        @Override
        public String getDescription() { return "/system/holidayModes/hm1/hcMode"; }
    },
    SYSHOLMODHM2ASSIGN {
        @Override
        public String toString() { return "SYSHOLMODHM2ASSIGN"; }

        @Override
        public String getDescription() { return "/system/holidayModes/hm2/assignedTo"; }
    },
    SYSHOLMODHM2DHW {
        @Override
        public String toString() { return "SYSHOLMODHM1DHW"; }

        @Override
        public String getDescription() { return "/system/holidayModes/hm2/dhwMode"; }
    },
    SYSHOLMODHM2HCMOD {
        @Override
        public String toString() { return "SYSHOLMODHM1HCMOD"; }

        @Override
        public String getDescription() { return "/system/holidayModes/hm1/hcMode"; }
    };

    public static KM200PublishTopic descriptionOf(String description){
        for(KM200PublishTopic topic : KM200PublishTopic.values()){
            if(topic.getDescription().equals(description)){
                return topic;
            }
        }
        return null;
    }
    public abstract String getDescription();
}
