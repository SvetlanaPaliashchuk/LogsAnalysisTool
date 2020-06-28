package by.yuntsevich.app.controller.command.impl;

import by.yuntsevich.app.controller.command.Command;
import by.yuntsevich.app.entity.LogRecord;
import by.yuntsevich.app.service.LogService;
import by.yuntsevich.app.service.ServiceException;
import by.yuntsevich.app.service.ServiceFactory;

import java.util.List;

public class GetAllLogs implements Command {
    @Override
    public String execute(String request) throws ServiceException {
        List<LogRecord> list;
        StringBuilder response = new StringBuilder();

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        LogService logService = serviceFactory.getLogService();



        try{
            list = logService.getLogsList(request);
            response.append("List of logs:\n");
            for (LogRecord logRecord : list) {
                response.append(logRecord);
                response.append("\n");
            }
        }catch(ServiceException e){
            throw new ServiceException(e);
        }
        return response.toString();
    }
}