package by.yuntsevich.app.controller;

import by.yuntsevich.app.controller.command.Command;
import by.yuntsevich.app.service.ServiceException;

public final class Controller {
    private final CommandProvider provider = new CommandProvider();
    private static final String paramDelimiter = ",";

    public String executeTask(String request) throws ServiceException {
        String commandName;
        Command executionCommand;

        if (request.contains(paramDelimiter)) {
            commandName = request.substring(0, request.indexOf(paramDelimiter));
        }
        else commandName = request;
        executionCommand = provider.getCommand(commandName);

        return executionCommand.execute(request);
    }
}