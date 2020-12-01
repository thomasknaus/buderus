package com.buderus.connection.call.publish;

public enum HolidayModeCircuit {
    HC1 ("hc1"), HC2 ("hc2"), DHW1 ("dhw1");

    private final String value;

    HolidayModeCircuit(String value ) { this.value = value; }

    public static HolidayModeCircuit contains(String value){
        if(HC1.value.equals(value)){
            return HolidayModeCircuit.HC1;
        } else if(HC2.value.equals(value)){
            return HolidayModeCircuit.HC2;
        } else if(DHW1.value.equals(value)){
            return HolidayModeCircuit.DHW1;
        } else {
            return HolidayModeCircuit.HC1;
        }
    }
}
