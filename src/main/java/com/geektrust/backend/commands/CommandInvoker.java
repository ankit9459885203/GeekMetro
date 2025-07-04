package com.geektrust.backend.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandInvoker {
    private final Map<String, ICommand> commandMap = new HashMap<>();

    public void register(String commandName, ICommand command) {
        commandMap.put(commandName, command);
    }

    public void executeCommand(String inputLine) {
        List<String> tokens = List.of(inputLine.split(" "));
        String commandName = tokens.get(0);

        ICommand command = commandMap.get(commandName);
        if (command != null) {
            command.execute(tokens);
        }
    }
}
