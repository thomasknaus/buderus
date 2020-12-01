package com.buderus.connection.call.publish;

public enum OperationModeHC {
    AUTO ("auto"), MANUAL ("manual");

    private final String value;

    OperationModeHC(String value ) { this.value = value; }

    public static OperationModeHC contains(String value){
        if(value.equals(AUTO.value)){
            return OperationModeHC.AUTO;
        } else if(value.equals(MANUAL.value)){
            return OperationModeHC.MANUAL;
        } else {
            return OperationModeHC.AUTO;
        }
    }

}
