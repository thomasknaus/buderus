package com.buderus.connection.call.publish;

public enum HCMode {
    AUTO_SAT ("AUTO_SAT"), FIX_TEMP ("FIX_TEMP"), OFF ("OFF"),  ECO ("ECO");

    private final String value;

    HCMode(String value ) { this.value = value; }

    public static HCMode contains(String value){
        if(AUTO_SAT.value.equals(value)){
            return HCMode.AUTO_SAT;
        } else if(FIX_TEMP.value.equals(value)){
            return HCMode.FIX_TEMP;
        } else if(OFF.value.equals(value)){
            return HCMode.OFF;
        } else if(ECO.value.equals(value)){
            return HCMode.ECO;
        } else {
            return HCMode.OFF;
        }
    }
}
