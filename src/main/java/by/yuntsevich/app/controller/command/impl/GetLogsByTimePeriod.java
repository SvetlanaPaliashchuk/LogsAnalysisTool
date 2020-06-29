package by.yuntsevich.app.controller.command.impl;

import by.yuntsevich.app.controller.command.Command;
import by.yuntsevich.app.entity.LogRecord;
import by.yuntsevich.app.service.LogService;
import by.yuntsevich.app.service.ServiceException;
import by.yuntsevich.app.service.ServiceFactory;

import java.util.List;

public class GetLogsByTimePeriod implements Command {
    @Override
    public String execute(String request) throws ServiceException {
        List<LogRecord> list;
        StringBuilder response = new StringBuilder();

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        LogService logService = serviceFactory.getLogService();

        String[] parts = request.split(",");
        String fileName = parts[1];
        String startDate = parts[2];
        String endDate = parts[3];

        try{
            list = logService.getLogsByTimePeriod(fileName, startDate, endDate);
            response.append("List of logs by time period from: ")
                    .append(startDate)
                    .append(" to ")
                    .append(endDate)
                    .append("\n");
            for (LogRecord logRecord : list) {
                response.append(logRecord.getUserName());
                response.append(";");
                response.append(logRecord.getDateTime());
                response.append(";");
                response.append(logRecord.getMessage());
                response.append("\n");
            }
        }catch(ServiceException e){
           return response.append(e.getMessage()).toString();
        }
        return response.toString();
    }
}
