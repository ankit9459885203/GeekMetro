package com.geektrust.backend.commands;

import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.StationType;
import com.geektrust.backend.services.MetroCardService;
import com.geektrust.backend.services.SummaryService;

import java.util.List;

public class CheckInCommand implements ICommand {

    private final MetroCardService metroCardService;
    private final SummaryService summaryService;

    public CheckInCommand(MetroCardService metroCardService, SummaryService summaryService) {
        this.metroCardService = metroCardService;
        this.summaryService = summaryService;
    }

    @Override
    public void execute(List<String> tokens) {
        String cardId = tokens.get(1);
        PassengerType type = PassengerType.valueOf(tokens.get(2));
        StationType station = StationType.valueOf(tokens.get(3));

        MetroCardService.JourneyResult result = metroCardService.processCheckIn(cardId, type, station);
       
        int baseFare = metroCardService.getFareService().getBaseFare(type);
        int discount = result.isReturn ? baseFare / 2 : 0;

        summaryService.updateSummary(station, type, result.fare, discount);

        if (result.rechargeFee > 0) {
            summaryService.addRechargeFee(station, result.rechargeFee);
        }
    }
}
