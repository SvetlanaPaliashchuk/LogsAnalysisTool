package by.yuntsevich.app.service.impl;

import by.yuntsevich.app.dao.DAOException;
import by.yuntsevich.app.dao.DaoFactory;
import by.yuntsevich.app.dao.LogDao;
import by.yuntsevich.app.entity.LogRecord;
import by.yuntsevich.app.service.LogService;
import by.yuntsevich.app.service.ServiceException;
import by.yuntsevich.app.service.util.LogParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogServiceImpl implements LogService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private LogDao logDao = daoFactory.getLogDao();


    @Override
    public List<LogRecord> getLogsList(String fileName) throws ServiceException {
        List<LogRecord> logRecords;
        LogParser lp = new LogParser();
        if (!fileName.equals("")) {
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
    public List<LogRecord> getLogsByTimePeriod(String fileName, String timePeriod) throws ServiceException {
        return null;
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

}
