package com.geektrust.backend.commands;


import com.geektrust.backend.repositories.MetroCardRepository;

import java.util.List;

public class BalanceCommand implements ICommand {

    private final MetroCardRepository metroCardRepository;

    public BalanceCommand(MetroCardRepository metroCardRepository) {
        this.metroCardRepository = metroCardRepository;
    }

    @Override
    public void execute(List<String> tokens) {
        String cardId = tokens.get(1);
        int balance = Integer.parseInt(tokens.get(2));
        metroCardRepository.addCard(cardId, balance);
    }
}
