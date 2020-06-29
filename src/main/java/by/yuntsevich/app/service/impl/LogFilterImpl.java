package by.yuntsevich.app.service.impl;

import by.yuntsevich.app.dao.DaoException;
import by.yuntsevich.app.dao.DaoFactory;
import by.yuntsevich.app.dao.LogDao;
import by.yuntsevich.app.entity.LogRecord;
import by.yuntsevich.app.service.LogFilter;
import by.yuntsevich.app.service.LogService;
import by.yuntsevich.app.service.ServiceException;
import by.yuntsevich.app.service.ServiceFactory;
import by.yuntsevich.app.service.util.LogParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogFilterImpl implements LogFilter {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private LogDao logDao = daoFactory.getLogDao();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    @Override
    public List<LogRecord> getLogsByUserName(String fileName, String userName) throws ServiceException {
        LogService service = serviceFactory.getLogService();
        List<LogRecord> logRecords = service.getLogsList(fileName).stream()
                .filter(item -> userName.equals(item.getUserName()))
                .collect(Collectors.toList());
        try {
            logDao.writeResultToFile(convertToResultList(logRecords));
        } catch (DaoException e) {
            throw new ServiceException("Could not write the list of logs", e);
        }
        return logRecords;

    }

    @Override
    public List<LogRecord> getLogsByTimePeriod(String fileName, String startDate, String endDate) throws ServiceException {
        List<LogRecord> logRecords;
        LogService service = serviceFactory.getLogService();
        try {
            logRecords = service.getLogsList(fileName)
                    .stream()
                    .filter(line -> isIncludedInRange(line, startDate, endDate))
                    .collect(Collectors.toList());
            logDao.writeResultToFile(convertToResultList(logRecords));
        } catch (DaoException e) {
            throw new ServiceException("Could not write the list of logs", e);
        } catch (DateTimeParseException e) {
            throw new ServiceException("Could not parse specified date", e);
        }
        return logRecords;
    }

    //check if the date is in the specified range
    private boolean isIncludedInRange(LogRecord logRecord, String startDate, String endDate) {
        LogParser lp = new LogParser();
        LocalDateTime startPeriod = lp.parseDateTime(startDate);
        LocalDateTime endPeriod = lp.parseDateTime(endDate);
        return ((logRecord.getDateTime().isAfter(startPeriod) && logRecord.getDateTime().isBefore(endPeriod)) ||
                logRecord.getDateTime().equals(startPeriod) ||
                logRecord.getDateTime().equals(endPeriod));
    }

    @Override
    public List<LogRecord> getLogsByMessagePattern(String fileName, String pattern) throws ServiceException {
        LogService service = serviceFactory.getLogService();
        List<LogRecord> logRecords = service.getLogsList(fileName).stream()
                .filter(item -> item.getMessage().matches(pattern))
                .collect(Collectors.toList());
        try {
            logDao.writeResultToFile(convertToResultList(logRecords));
        } catch (DaoException e) {
            throw new ServiceException("Could not write the list of logs", e);
        }
        return logRecords;
    }


    private List<String> convertToResultList(List<LogRecord> logRecords) {
        List<String> result = new ArrayList<>();
        for (LogRecord logRecord : logRecords) {
            result.add(logRecord.getUserName() + ";"
                    + logRecord.getDateTime().toString() + ";"
                    + logRecord.getMessage());
        }
        result.add("----------------------------------------------");
        return result;
    }

}
