package com.lisi4ka.models;

public enum StandardOfLiving {
    ULTRA_HIGH,
    HIGH,
    VERY_LOW,
    ULTRA_LOW,
    NIGHTMARE;
    public static StandardOfLiving fromInt(int standardOfLivingNumber){
        if (standardOfLivingNumber == 1) {
            return StandardOfLiving.ULTRA_HIGH;
        } else if (standardOfLivingNumber == 2) {
            return StandardOfLiving.HIGH;
        } else if (standardOfLivingNumber == 3) {
            return StandardOfLiving.VERY_LOW;
        } else if (standardOfLivingNumber == 4) {
            return StandardOfLiving.ULTRA_LOW;
        } else if (standardOfLivingNumber == 5) {
            return StandardOfLiving.NIGHTMARE;
        } else {
            return null;
        }
    }
}
