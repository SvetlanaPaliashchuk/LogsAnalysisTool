package by.yuntsevich.app.service;

import by.yuntsevich.app.entity.LogRecord;

import java.util.List;
import java.util.Map;

public interface LogService {
    List<LogRecord> getLogsList(String fileName) throws ServiceException;
    List<LogRecord> getLogsByUserName (String fileName, String userName) throws ServiceException;
    List<LogRecord> getLogsByTimePeriod (String fileName, String startDate, String endDate) throws ServiceException;
    List<LogRecord> getLogsByMessagePattern (String fileName, String pattern) throws ServiceException;
    List<String> groupLogsByUserName (String fileName) throws ServiceException;


}
