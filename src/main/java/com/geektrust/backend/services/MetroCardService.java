package com.geektrust.backend.services;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.StationType;
import com.geektrust.backend.repositories.MetroCardRepository;

import java.util.HashMap;
import java.util.Map;

public class MetroCardService {

    private final MetroCardRepository metroCardRepository;
    private final FareService fareService;
    private final Map<String, StationType> lastStationVisited;

    public MetroCardService(MetroCardRepository metroCardRepository, FareService fareService) {
        this.metroCardRepository = metroCardRepository;
        this.fareService = fareService;
        this.lastStationVisited = new HashMap<>();
    }

    public JourneyResult processCheckIn(String cardId, PassengerType type, StationType station) {
        MetroCard card = metroCardRepository.getCard(cardId);

        boolean isReturn = lastStationVisited.containsKey(cardId)
                && lastStationVisited.get(cardId) != station;

        int fare = fareService.getFare(type, isReturn);
        int balance = card.getBalance();
        int rechargeAmount = 0;
        int rechargeFee = 0;

        if (balance < fare) {
            int shortfall = fare - balance;
            while (true) {
                int fee = fareService.getRechargeFee(shortfall);
                if (shortfall + balance >= fare + fee) {
                    rechargeAmount = shortfall;
                    rechargeFee = fee;
                    break;
                }
                shortfall++;
            }
            card.recharge(rechargeAmount);
            card.deduct(rechargeFee); // deduct only fee separately
        }

        card.deduct(fare);

        // Only update station if not a return journey
        if (!isReturn) {
            lastStationVisited.put(cardId, station);
        }

        return new JourneyResult(fare, rechargeFee, isReturn);
    }

    public FareService getFareService() {
        return this.fareService;
    }

    public static class JourneyResult {
        public final int fare;
        public final int rechargeFee;
        public final boolean isReturn;

        public JourneyResult(int fare, int rechargeFee, boolean isReturn) {
            this.fare = fare;
            this.rechargeFee = rechargeFee;
            this.isReturn = isReturn;
        }
    }
}
