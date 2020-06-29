package by.yuntsevich.app.controller;

import by.yuntsevich.app.controller.command.Command;
import by.yuntsevich.app.controller.command.CommandName;
import by.yuntsevich.app.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

final class CommandProvider {
    private final Map<CommandName, Command> repository = new HashMap<>();

    CommandProvider(){
        repository.put(CommandName.GET_ALL_LOGS, new GetAllLogs());
        repository.put(CommandName.GET_LOGS_BY_USERNAME, new GetLogsByUserName());
        repository.put(CommandName.GET_LOGS_BY_MESSAGE_PATTERN, new GetLogsByMessagePattern());
        repository.put(CommandName.GET_LOGS_BY_TIME_PERIOD, new GetLogsByTimePeriod());
        repository.put(CommandName.GROUP_LOGS_BY_USERNAME, new GroupLogsByUserName());
        repository.put(CommandName.GROUP_LOGS_BY_TIME_UNIT, new GroupLogsByTimeUnit());
    }

    Command getCommand(String name){
        CommandName commandName;
        Command command;

        try{
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        }
        catch (IllegalArgumentException | NullPointerException e){
            command = repository.get(CommandName.WRONG_REQUEST);
        }

        return command;
    }
}