package com.buderus.connection.call.publish;

public enum SuWiSwitchMode {
    OFF ("off"), AUTOMATIC ("automatic"), FORCED ("forced");

    private final String value;

    SuWiSwitchMode(String value ) { this.value = value; }

    public static SuWiSwitchMode contains(String value){
        if(OFF.value.equals(value)){
            return SuWiSwitchMode.OFF;
        } else if(AUTOMATIC.value.equals(value)){
            return SuWiSwitchMode.AUTOMATIC;
        } else if(FORCED.value.equals(value)){
            return SuWiSwitchMode.FORCED;
        } else {
            return SuWiSwitchMode.AUTOMATIC;
        }
    }
}
