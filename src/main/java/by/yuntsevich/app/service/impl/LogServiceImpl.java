package by.yuntsevich.app.service.impl;

import by.yuntsevich.app.dao.DAOException;
import by.yuntsevich.app.dao.DaoFactory;
import by.yuntsevich.app.dao.LogDao;
import by.yuntsevich.app.entity.LogRecord;
import by.yuntsevich.app.entity.LogsTimeUnit;
import by.yuntsevich.app.service.LogService;
import by.yuntsevich.app.service.ServiceException;
import by.yuntsevich.app.service.util.LogParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogServiceImpl implements LogService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private LogDao logDao = daoFactory.getLogDao();


    @Override
    public List<LogRecord> getLogsList(String fileName) throws ServiceException {
        List<LogRecord> logRecords;
        LogParser lp = new LogParser();
        if (!fileName.equals("dir")) {
            try {
                logRecords = lp.createLogRecordList(logDao.getLogsFromFile(fileName));
                if (logRecords == null) {
                    throw new ServiceException("no logs found");
                }
            } catch (DAOException e) {
                throw new ServiceException("Could not get the list of logs", e);
            }
        } else {
            try {
                logRecords = lp.createLogRecordList(logDao.getLogsFromDirectory());
                if (logRecords == null) {
                    throw new ServiceException("no logs found");
                }
            } catch (DAOException e) {
                throw new ServiceException("Could not get the list of logs", e);
            }
        }

        return logRecords;
    }


    //filter methods
    @Override
    public List<LogRecord> getLogsByUserName(String fileName, String userName) throws ServiceException {
        List<LogRecord> logRecords = getLogsList(fileName).stream()
                .filter(item -> userName.equals(item.getUserName()))
                .collect(Collectors.toList());
        try {
            logDao.writeResultToFile(convertToResultList(logRecords));
        } catch (DAOException e) {
            throw new ServiceException("Could not write the list of logs", e);
        }
        return logRecords;

    }

    @Override
    public List<LogRecord> getLogsByTimePeriod(String fileName, String startDate, String endDate) throws ServiceException {
        List<LogRecord> logRecords;
        try {
            logRecords = getLogsList(fileName)
                    .stream()
                    .filter(line -> isIncludedInRange(line, startDate, endDate))
                    .collect(Collectors.toList());
            logDao.writeResultToFile(convertToResultList(logRecords));
        } catch (DAOException e) {
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
        List<LogRecord> logRecords = getLogsList(fileName).stream()
                .filter(item -> item.getMessage().matches(pattern))
                .collect(Collectors.toList());
        try {
            logDao.writeResultToFile(convertToResultList(logRecords));
        } catch (DAOException e) {
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


    //grouping methods
    @Override
    public List<String> groupLogsByUserName(String fileName) throws ServiceException {
        Map<String, Long> resultMap = getLogsList(fileName).stream()
                .collect(Collectors.groupingBy(LogRecord::getUserName, Collectors.counting()));
        List<String> result = new ArrayList<>();

        for (Map.Entry entry : resultMap.entrySet()) {
            result.add(entry.getKey().toString() + "      " + entry.getValue().toString());
        }
        try {
            logDao.writeResultToFile(result);
        } catch (DAOException e) {
            throw new ServiceException("Could not write the list of logs", e);
        }
        result.add("----------------------------------------------");

        return result;
    }

    @Override
    public List<String> groupLogsByTimeUnit(String fileName, String timeUnit) throws ServiceException {
        List<LogRecord> logsList = setTruncatedDate(fileName, timeUnit);
        Map<LocalDateTime, Long> resultMap = logsList.stream()
                .collect(Collectors.groupingBy(e ->
                        LocalDateTime.from(e.getDateTime()), Collectors.counting())
                );

        List<String> result = new ArrayList<>();
        for (Map.Entry entry : resultMap.entrySet()) {
            result.add(entry.getKey().toString() + "              " + entry.getValue().toString());
        }
        result.add("----------------------------------------------");

        try {
            logDao.writeResultToFile(result);
        } catch (DAOException e) {
            throw new ServiceException("Could not write the list of logs", e);
        }
        return result;
    }

    private List<LogRecord> setTruncatedDate(String fileName, String timeUnit) throws ServiceException {
        LocalDateTime date;
        List<LogRecord> logsList = getLogsList(fileName);

        for (LogRecord log : logsList) {
            switch (timeUnit) {
                case "HOUR":
                    date = log.getDateTime().truncatedTo(ChronoUnit.HOURS);
                    break;
                case "DAY":
                    date = log.getDateTime().truncatedTo(ChronoUnit.DAYS);
                    break;
                case "MONTH":
                    date = log.getDateTime().truncatedTo(ChronoUnit.DAYS).withDayOfMonth(1);
                    break;
                case "YEAR":
                    date = log.getDateTime().truncatedTo(ChronoUnit.DAYS).withDayOfYear(1);
                    break;
                default:
                    date = null;
            }
            log.setDateTime(date);
        }
        return logsList;
    }

}
