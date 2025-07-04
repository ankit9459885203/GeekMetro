
package com.geektrust.backend.services;

import com.geektrust.backend.entities.PassengerType;

import java.util.HashMap;
import java.util.Map;

public class FareService {

    private static final Map<PassengerType, Integer> BASE_FARES = new HashMap<>();
    static {
        BASE_FARES.put(PassengerType.ADULT, 200);
        BASE_FARES.put(PassengerType.SENIOR_CITIZEN, 100);
        BASE_FARES.put(PassengerType.KID, 50);
    }

    public int getFare(PassengerType type, boolean isReturn) {
        int base = BASE_FARES.get(type);
        return isReturn ? base / 2 : base;
    }

    public int getAutoRechargeAmount(int required, int available) {
        return required - available;
    }

    public int getRechargeFee(int rechargeAmount) {
        return (int) Math.ceil((double) rechargeAmount * 0.02);

    }

    public int getBaseFare(PassengerType type) {
        return BASE_FARES.get(type);
    }
}
