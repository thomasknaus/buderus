package com.buderus.connection.call.publish;

public enum OperationModeDHW {
    OFF ("Off"), LOW ("low"), HIGH ("high"), HCPROGRAM("HCprogram"), OWNPROGRAM("ownprogram");

    private final String value;

    OperationModeDHW(String value ) { this.value = value; }

    public static OperationModeDHW contains(String value){
        if(OFF.value.equals(value)){
            return OperationModeDHW.OFF;
        } else if(LOW.value.equals(value)){
            return OperationModeDHW.LOW;
        } else if(HIGH.value.equals(value)){
            return OperationModeDHW.HIGH;
        } else if(HCPROGRAM.value.equals(value)){
            return OperationModeDHW.HCPROGRAM;
        } else if(OWNPROGRAM.value.equals(value)){
            return OperationModeDHW.OWNPROGRAM;
        } else {
            return OperationModeDHW.OFF;
        }
    }
}
