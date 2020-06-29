package by.yuntsevich.app.controller.command.impl;

import by.yuntsevich.app.controller.command.Command;
import by.yuntsevich.app.service.LogGrouper;
import by.yuntsevich.app.service.ServiceException;
import by.yuntsevich.app.service.ServiceFactory;

import java.util.List;

public class GroupLogsByTimeUnit implements Command {
    private static final String DELIMITER = ",";

    @Override
    public String execute(String request) {
        List<String> list;
        StringBuilder response = new StringBuilder();

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        LogGrouper logGrouper = serviceFactory.getLogGrouper();

        String[] parts = request.split(DELIMITER);
        String fileName = parts[1];
        String timeUnit = parts[2];

        try {
            list = logGrouper.groupLogsByTimeUnit(fileName, timeUnit);
            response.append("Grouped logs by ").append(timeUnit).append(":\n");
            response.append("Time unit");
            response.append("              ");
            response.append("Count of logs");
            response.append("\n");

            for (String item : list) {
                response.append(item);
                response.append("\n");
            }
        } catch (ServiceException e) {
            return response.append(e.getMessage()).toString();
        }
        return response.toString();
    }
}