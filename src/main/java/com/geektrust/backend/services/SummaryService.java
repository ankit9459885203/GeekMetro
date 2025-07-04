package com.geektrust.backend.services;

import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.StationType;

import java.util.HashMap;
import java.util.Map;

public class SummaryService {

    private final Map<StationType, Integer> collectionMap = new HashMap<>();
    private final Map<StationType, Integer> discountMap = new HashMap<>();
    private final Map<StationType, Map<PassengerType, Integer>> passengerCountMap = new HashMap<>();

    public void updateSummary(StationType station, PassengerType type, int amountCollected, int discountGiven) {
        collectionMap.put(station, collectionMap.getOrDefault(station, 0) + amountCollected);
        discountMap.put(station, discountMap.getOrDefault(station, 0) + discountGiven);

        passengerCountMap.putIfAbsent(station, new HashMap<>());
        Map<PassengerType, Integer> countMap = passengerCountMap.get(station);
        countMap.put(type, countMap.getOrDefault(type, 0) + 1);
    }

    public void addRechargeFee(StationType station, int fee) {
        collectionMap.put(station, collectionMap.getOrDefault(station, 0) + fee);
    }

    public void printSummary() {
        for (StationType station : StationType.values()) {
            int totalCollection = collectionMap.getOrDefault(station, 0);
            int totalDiscount = discountMap.getOrDefault(station, 0);

            System.out.println("TOTAL_COLLECTION " + station + " " + totalCollection + " " + totalDiscount);
            System.out.println("PASSENGER_TYPE_SUMMARY");

            Map<PassengerType, Integer> countMap = passengerCountMap.getOrDefault(station, new HashMap<>());

            countMap.entrySet().stream()
                    .sorted((e1, e2) -> {
                        int compareCount = Integer.compare(e2.getValue(), e1.getValue());
                        if (compareCount == 0) {
                            return e1.getKey().name().compareTo(e2.getKey().name());
                        }
                        return compareCount;
                    })
                    .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
        }
    }
}
