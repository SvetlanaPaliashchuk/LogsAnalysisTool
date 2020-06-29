package by.yuntsevich.app.service;

import by.yuntsevich.app.entity.LogRecord;

import java.util.List;

public interface LogService {
    List<LogRecord> getLogsList(String fileName) throws ServiceException;


}
