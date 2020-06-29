package by.yuntsevich.app.service;

import by.yuntsevich.app.entity.LogRecord;

import java.util.List;

public interface LogFilter {
    List<LogRecord> getLogsByUserName (String fileName, String userName) throws ServiceException;
    List<LogRecord> getLogsByTimePeriod (String fileName, String startDate, String endDate) throws ServiceException;
    List<LogRecord> getLogsByMessagePattern (String fileName, String pattern) throws ServiceException;

}
