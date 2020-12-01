package com.buderus.connection.call.publish;

public enum DHWMode {
    AUTO_SAT ("AUTO_SAT"), OFF ("OFF"), TD_OFF ("TD_OFF");

    private final String value;

    DHWMode(String value ) { this.value = value; }

    public static DHWMode contains(String value){
        if(OFF.value.equals(value)){
            return DHWMode.OFF;
        } else if(AUTO_SAT.value.equals(value)){
            return DHWMode.AUTO_SAT;
        } else if(TD_OFF.value.equals(value)){
            return DHWMode.TD_OFF;
        } else {
            return DHWMode.OFF;
        }
    }
}
