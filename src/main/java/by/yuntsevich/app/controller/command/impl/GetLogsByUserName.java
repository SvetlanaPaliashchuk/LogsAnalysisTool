package by.yuntsevich.app.controller.command.impl;

import by.yuntsevich.app.controller.command.Command;
import by.yuntsevich.app.entity.LogRecord;
import by.yuntsevich.app.service.LogFilter;
import by.yuntsevich.app.service.LogService;
import by.yuntsevich.app.service.ServiceException;
import by.yuntsevich.app.service.ServiceFactory;

import java.util.List;

public class GetLogsByUserName implements Command {
    private static final String DELIMITER = ",";

    @Override
    public String execute(String request) {
        List<LogRecord> list;
        StringBuilder response = new StringBuilder();

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        LogFilter logFilter = serviceFactory.getLogFilter();

        String[] parts = request.split(DELIMITER);
        String fileName = parts[1];
        String userName = parts[2];
        try {
            list = logFilter.getLogsByUserName(fileName, userName);
            response.append("List of logs by username:\n");
            for (LogRecord logRecord : list) {
                response.append(logRecord.getUserName());
                response.append(";");
                response.append(logRecord.getDateTime());
                response.append(";");
                response.append(logRecord.getMessage());
                response.append("\n");
            }
        } catch (ServiceException e) {
            return response.append(e.getMessage()).toString();
        }
        return response.toString();
    }
}
