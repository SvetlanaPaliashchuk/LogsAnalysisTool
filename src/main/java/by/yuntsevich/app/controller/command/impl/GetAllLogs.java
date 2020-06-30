package by.yuntsevich.app.controller.command.impl;

import by.yuntsevich.app.controller.command.Command;
import by.yuntsevich.app.entity.LogRecord;
import by.yuntsevich.app.service.LogService;
import by.yuntsevich.app.service.ServiceException;
import by.yuntsevich.app.service.ServiceFactory;

import java.util.List;

public class GetAllLogs implements Command {
    private static final String DELIMITER = ",";
    @Override
    public String execute(String request) {
        List<LogRecord> list;
        StringBuilder response = new StringBuilder();

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        LogService logService = serviceFactory.getLogService();
        String[] parts = request.split(DELIMITER);
        String fileName = parts[1];
        try {
            list = logService.getLogsList(fileName);
            response.append("List of logs:\n");
            for (LogRecord logRecord : list) {
                response.append(logRecord);
                response.append("\n");
            }
        } catch (ServiceException e) {
            return response.append(e.getMessage()).toString();
        }
        return response.toString();
    }
}