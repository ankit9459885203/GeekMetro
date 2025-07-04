package com.geektrust.backend.repositories;



import com.geektrust.backend.entities.MetroCard;

import java.util.HashMap;
import java.util.Map;

public class MetroCardRepository {
    private final Map<String, MetroCard> cardMap = new HashMap<>();

    public void addCard(String id, int balance) {
        cardMap.put(id, new MetroCard(id, balance));
    }

    public MetroCard getCard(String id) {
        return cardMap.get(id);
    }

    public boolean exists(String id) {
        return cardMap.containsKey(id);
    }
}
