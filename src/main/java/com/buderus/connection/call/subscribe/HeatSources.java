package com.buderus.connection.call.subscribe;

public enum HeatSources implements KM200SubscribeValues {
    HEATSRCACTCHPOW {
        @Override
        public String toString() {
            return "HEATSRCACTCHPOW";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/actualCHPower";
        }
    },
    HEATSRCACTDHWPOW {
        @Override
        public String toString() {
            return "HEATSRCACTDHWPOW";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/actualDHWPower";
        }
    },
    HEATSRCACTMODULA {
        @Override
        public String toString() {
            return "HEATSRCACTMODULA";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/actualModulation";
        }
    },
    HEATSRCACTPOW {
        @Override
        public String toString() {
            return "HEATSRCACTPOW";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/actualPower";
        }
    },
    HEATSRCACTSUPTMP {
        @Override
        public String toString() {
            return "HEATSRCACTSUPTMP";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/actualSupplyTemperature";
        }
    },
    HEATSRCBURNMODULASET {
        @Override
        public String toString() {
            return "HEATSRCBURNMODULASET";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/burnerModulationSetpoint";
        }
    },
    HEATSRCBURNPOWSET {
        @Override
        public String toString() {
            return "HEATSRCBURNPOWSET";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/burnerPowerSetpoint";
        }
    },
    HEATSRCCHIMNEYSWEEP {
        @Override
        public String toString() {
            return "HEATSRCCHIMNEYSWEEP";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/ChimneySweeper";
        }
    },
    HEATSRCCHPUMPMODULA {
        @Override
        public String toString() {
            return "HEATSRCCHPUMPMODULA";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/CHpumpModulation";
        }
    },
    HEATSRCFLAMECUR {
        @Override
        public String toString() {
            return "HEATSRCFLAMECUR";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/flameCurrent";
        }
    },
    HEATSRCFLAMESTAT {
        @Override
        public String toString() {
            return "HEATSRCFLAMESTAT";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/flameStatus";
        }
    },
    HEATSRCGASAIRPRESS {
        @Override
        public String toString() {
            return "HEATSRCGASAIRPRESS";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/gasAirPressure";
        }
    },
    HEATSRCHS1 {
        @Override
        public String toString() {
            return "HEATSRCHS1";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1";
        }
    },
    HEATSRCHS1ACTCHPOW {
        @Override
        public String toString() {
            return "HEATSRCHS1ACTCHPOW";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/actualCHPower";
        }
    },
    HEATSRCHS1ACTDHWPOW {
        @Override
        public String toString() {
            return "HEATSRCHS1ACTDHWPOW";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/actualDHWPower";
        }
    },
    HEATSRCHS1ACTMODULA {
        @Override
        public String toString() {
            return "HEATSRCHS1ACTMODULA";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/actualModulation";
        }
    },
    HEATSRCHS1ACTPOW {
        @Override
        public String toString() {
            return "HEATSRCHS1ACTPOW";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/actualPower";
        }
    },
    HEATSRCHS1CHPUMPMODULA {
        @Override
        public String toString() {
            return "HEATSRCHS1CHPUMPMODULA";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/CHpumpModulation";
        }
    },
    HEATSRCHS1ENGRESVOIR {
        @Override
        public String toString() {
            return "HEATSRCHS1ENGRESVOIR";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/energyReservoir";
        }
    },
    HEATSRCHS1FLAMESTAT {
        @Override
        public String toString() {
            return "HEATSRCHS1FLAMESTAT";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/flameStatus";
        }
    },
    HEATSRCHS1FUEL {
        @Override
        public String toString() {
            return "HEATSRCHS1FUEL";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/fuel";
        }
    },
    HEATSRCHS1FUELCALVALUE {
        @Override
        public String toString() {
            return "HEATSRCHS1FUELCALVALUE";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/fuel/caloricValue";
        }
    },
    HEATSRCHS1FUELDENSITY {
        @Override
        public String toString() {
            return "HEATSRCHS1FUELDENSITY";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/fuel/density";
        }
    },
    HEATSRCHS1FUELCONCORFACT {
        @Override
        public String toString() {
            return "HEATSRCHS1FUELCONCORFACT";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/fuelConsmptCorrFactor";
        }
    },
    HEATSRCHS1INFO {
        @Override
        public String toString() {
            return "HEATSRCHS1INFO";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/info";
        }
    },
    HEATSRCHS1NOMCHPOW {
        @Override
        public String toString() {
            return "HEATSRCHS1NOMCHPOW";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/nominalCHPower";
        }
    },
    HEATSRCHS1NOMDHWPOW {
        @Override
        public String toString() {
            return "HEATSRCHS1NOMDHWPOW";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/nominalDHWPower";
        }
    },
    HEATSRCHS1NOMFUELCONS {
        @Override
        public String toString() {
            return "HEATSRCHS1NOMFUELCONS";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/nominalFuelConsumption";
        }
    },
    HEATSRCHS1NUMBOFSTART {
        @Override
        public String toString() {
            return "HEATSRCHS1NUMBOFSTART";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/numberOfStarts";
        }
    },
    HEATSRCHS1RESVOIRALERT {
        @Override
        public String toString() {
            return "HEATSRCHS1RESVOIRALERT";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/reservoirAlert";
        }
    },
    HEATSRCHS1SUPTMPSET {
        @Override
        public String toString() {
            return "HEATSRCHS1SUPTMPSET";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/supplyTemperatureSetpoint";
        }
    },
    HEATSRCHS1TYPE {
        @Override
        public String toString() {
            return "HEATSRCHS1TYPE";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/hs1/type";
        }
    },
    HEATSRCINFO {
        @Override
        public String toString() {
            return "HEATSRCINFO";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/info";
        }
    },
    HEATSRCNOMCHPOW {
        @Override
        public String toString() {
            return "HEATSRCNOMCHPOW";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/nominalCHPower";
        }
    },
    HEATSRCNOMDHWPOW {
        @Override
        public String toString() {
            return "HEATSRCNOMDHWPOW";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/nominalDHWPower";
        }
    },
    HEATSRCNUMOFSTART {
        @Override
        public String toString() {
            return "HEATSRCNUMOFSTART";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/numberOfStarts";
        }
    },
    HEATSRCPOWSET {
        @Override
        public String toString() {
            return "HEATSRCPOWSET";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/powerSetpoint";
        }
    },
    HEATSRCRETTMP {
        @Override
        public String toString() {
            return "HEATSRCRETTMP";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/returnTemperature";
        }
    },
    HEATSRCSUPTMPSET {
        @Override
        public String toString() {
            return "HEATSRCSUPTMPSET";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/supplyTemperatureSetpoint";
        }
    },
    HEATSRCSYSPRESS {
        @Override
        public String toString() {
            return "HEATSRCSYSPRESS";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/systemPressure";
        }
    },
    HEATSRCWORKTIME {
        @Override
        public String toString() {
            return "HEATSRCWORKTIME";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/workingTime";
        }
    },
    HEATSRCWORKTIMECENTRHEAT {
        @Override
        public String toString() {
            return "HEATSRCWORKTIMECENTRHEAT";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/workingTime/centralHeating";
        }
    },
    HEATSRCWORKTIMESECBURNER {
        @Override
        public String toString() {
            return "HEATSRCWORKTIMESECBURNER";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/workingTime/secondBurner";
        }
    },
    HEATSRCWORKTIMETOTALSYS {
        @Override
        public String toString() {
            return "HEATSRCWORKTIMETOTALSYS";
        }

        @Override
        public String getDescription() {
            return "km200/status/heatSources/workingTime/totalSystem";
        }
    };

    public HeatSources descriptionOf(String description) {
        for (HeatSources topic : HeatSources.values()) {
            if (topic.getDescription().equals(description)) {
                return topic;
            }
        }
        return null;
    }

    public abstract String getDescription();

}
