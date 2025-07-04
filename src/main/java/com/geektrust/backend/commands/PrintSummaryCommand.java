package com.geektrust.backend.commands;



import com.geektrust.backend.services.SummaryService;

import java.util.List;

public class PrintSummaryCommand implements ICommand {

    private final SummaryService summaryService;

    public PrintSummaryCommand(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @Override
    public void execute(List<String> tokens) {
        System.out.println("[DEBUG] PRINT_SUMMARY command executing...");
        summaryService.printSummary();
    }
}
