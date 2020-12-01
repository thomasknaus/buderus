package com.buderus.connection.call.subscribe;

public enum HolidayMode implements KM200SubscribeValues {
    SYSHOLIDAYMOD {
        @Override
        public String toString() {
            return "SYSHOLIDAYMOD";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes";
        }
    },
    SYSHOLIDAYMODHM1 {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM1";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm1";
        }
    },
    SYSHOLIDAYMODHM1DEL {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM1DEL";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm1/delete";
        }
    },
    SYSHOLIDAYMODHM1STARTSTOP {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM1STARTSTOP";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm1/startStop";
        }
    },
    SYSHOLIDAYMODHM2 {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM2";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm2";
        }
    },
    SYSHOLIDAYMODHM2DEL {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM2DEL";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm2/delete";
        }
    },
    SYSHOLIDAYMODHM2STARTSTOP {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM2STARTSTOP";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm2/startStop";
        }
    },
    SYSHOLIDAYMODHM3 {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM3";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm3";
        }
    },
    SYSHOLIDAYMODHM3DEL {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM3DEL";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm3/delete";
        }
    },
    SYSHOLIDAYMODHM3STARTSTOP {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM3STARTSTOP";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm3/startStop";
        }
    },
    SYSHOLIDAYMODHM4 {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM4";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm4";
        }
    },
    SYSHOLIDAYMODHM4DEL {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM4DEL";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm4/delete";
        }
    },
    SYSHOLIDAYMODHM4STARTSTOP {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM4STARTSTOP";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm4/startStop";
        }
    },
    SYSHOLIDAYMODHM5 {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM5";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm5";
        }
    },
    SYSHOLIDAYMODHM5DEL {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM5DEL";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm5/delete";
        }
    },
    SYSHOLIDAYMODHM5STARTSTOP {
        @Override
        public String toString() {
            return "SYSHOLIDAYMODHM5STARTSTOP";
        }

        @Override
        public String getDescription() {
            return "km200/status/system/holidayModes/hm5/startStop";
        }
    };

    public HolidayMode descriptionOf(String description) {
        for (HolidayMode topic : HolidayMode.values()) {
            if (topic.getDescription().equals(description)) {
                return topic;
            }
        }
        return null;
    }

    public abstract String getDescription();
}
