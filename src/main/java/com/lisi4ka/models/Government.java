package com.lisi4ka.models;

public enum Government {
    ANARCHY,
    COMMUNISM,
    MERITOCRACY,
    ETHNOCRACY;
    public static Government fromInt(int governmentNumber) {
        if (governmentNumber == 1) {
            return Government.ANARCHY;
        } else if (governmentNumber == 2) {
            return Government.COMMUNISM;
        } else if (governmentNumber == 3) {
            return Government.MERITOCRACY;
        } else if (governmentNumber == 4){
            return Government.ETHNOCRACY;
        } else{
            return null;
        }
    }
}