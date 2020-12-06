package com.buderus.connection.call.publish;

public enum Circuit {
    Heating_Circuit_1 ("1"), Heating_Circuit_2 ("2");


    private final String value;

    Circuit(String value ) { this.value = value; }

    public static Circuit contains(String value){
        if(Heating_Circuit_1.value.equals(value)){
            return Circuit.Heating_Circuit_1;
        } else if(Heating_Circuit_2.value.equals(value)){
            return Circuit.Heating_Circuit_2;
        }
        return null;
    }
}
