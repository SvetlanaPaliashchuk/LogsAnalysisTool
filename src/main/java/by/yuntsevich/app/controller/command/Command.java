package by.yuntsevich.app.controller.command;

import by.yuntsevich.app.service.ServiceException;

public interface Command {
    String execute(String request) throws ServiceException;
}