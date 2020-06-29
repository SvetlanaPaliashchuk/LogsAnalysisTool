package by.yuntsevich.app.controller.command.impl;

import by.yuntsevich.app.controller.command.Command;
import by.yuntsevich.app.entity.LogsTimeUnit;
import by.yuntsevich.app.service.LogService;
import by.yuntsevich.app.service.ServiceException;
import by.yuntsevich.app.service.ServiceFactory;
import java.time.temporal.ChronoUnit;

import java.util.List;

public class GroupLogsByTimeUnit implements Command {
    @Override
    public String execute(String request) {
        List<String> list;
        StringBuilder response = new StringBuilder();

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        LogService logService = serviceFactory.getLogService();

        String[] parts = request.split(",");

        String fileName = parts[1];
        String timeUnit = LogsTimeUnit.valueOf(parts[2]).toString();



        try {
            list = logService.groupLogsByTimeUnit(fileName, timeUnit);
            response.append("Grouped logs by time unit:\n");
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