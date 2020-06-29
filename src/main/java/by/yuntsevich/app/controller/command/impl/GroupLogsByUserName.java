package by.yuntsevich.app.controller.command.impl;

import by.yuntsevich.app.controller.command.Command;
import by.yuntsevich.app.entity.LogRecord;
import by.yuntsevich.app.service.LogService;
import by.yuntsevich.app.service.ServiceException;
import by.yuntsevich.app.service.ServiceFactory;

import java.text.Format;
import java.util.List;
import java.util.Map;

public class GroupLogsByUserName implements Command {
    @Override
    public String execute(String request) {
        List<String> list;
        StringBuilder response = new StringBuilder();

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        LogService logService = serviceFactory.getLogService();

        String[] parts = request.split(",");
        String fileName = parts[1];
        try {
            list = logService.groupLogsByUserName(fileName);
            response.append("Grouped logs by username:\n");
            response.append("Username");
            response.append("      ");
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
