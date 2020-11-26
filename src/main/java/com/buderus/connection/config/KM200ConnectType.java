package com.buderus.connection.config;

public enum KM200ConnectType {
    OFF ("0"), AUTO ("1"), ON ("2");

    private final String value;

    KM200ConnectType(String value ) { this.value = value; }

    public static KM200ConnectType contains(String value){
        if(value.equals(OFF.value)){
            return KM200ConnectType.OFF;
        } else if(value.equals(ON.value)){
            return KM200ConnectType.ON;
        } else {
            return KM200ConnectType.AUTO;
        }
    }
}
