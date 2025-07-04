package com.geektrust.backend;


import com.geektrust.backend.commands.*;
import com.geektrust.backend.repositories.MetroCardRepository;
import com.geektrust.backend.services.FareService;
import com.geektrust.backend.services.MetroCardService;
import com.geektrust.backend.services.SummaryService;
import com.geektrust.backend.utils.InputReaderUtil;

import java.io.IOException;
import java.util.List;
public class App {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Input file path not provided!");
            return;
        }

        String filePath = args[0];
        System.out.println("[DEBUG] Trying to read file: " + filePath);
        List<String> lines = InputReaderUtil.readInput(filePath);
        System.out.println("[DEBUG] Lines read: " + lines.size());

        MetroCardRepository metroCardRepository = new MetroCardRepository();
        FareService fareService = new FareService();
        SummaryService summaryService = new SummaryService();
        MetroCardService metroCardService = new MetroCardService(metroCardRepository, fareService);

        CommandInvoker invoker = new CommandInvoker();
        invoker.register("BALANCE", new BalanceCommand(metroCardRepository));
        invoker.register("CHECK_IN", new CheckInCommand(metroCardService, summaryService));
        invoker.register("PRINT_SUMMARY", new PrintSummaryCommand(summaryService));

        for (String line : lines) {
            System.out.println("[DEBUG] Executing line: " + line);
            invoker.executeCommand(line);
        }
    }
}
