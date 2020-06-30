package by.yuntsevich.app.service.impl;

import by.yuntsevich.app.dao.DaoException;
import by.yuntsevich.app.dao.DaoFactory;
import by.yuntsevich.app.dao.LogDao;
import by.yuntsevich.app.entity.LogRecord;
import by.yuntsevich.app.service.LogService;
import by.yuntsevich.app.service.ServiceException;
import by.yuntsevich.app.service.util.LogParser;

import java.util.ArrayList;
import java.util.List;

public class LogServiceImpl implements LogService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private LogDao logDao = daoFactory.getLogDao();

    @Override
    public List<LogRecord> getLogsList(String fileName) throws ServiceException {
        if (fileName == null) {
            throw new ServiceException("File cannot be null");
        }
        List<LogRecord> logRecords;
        LogParser lp = new LogParser();
        if (!fileName.equals("dir")) {
            try {
                logRecords = lp.createLogRecordList(logDao.getLogsFromFile(fileName));
                if (logRecords == null) {
                    throw new ServiceException("No logs found");
                }
            } catch (DaoException e) {
                throw new ServiceException("Could not get the list of logs", e);
            }
        } else {
            try {
                logRecords = lp.createLogRecordList(logDao.getLogsFromDirectory());
                if (logRecords == null) {
                    throw new ServiceException("No logs found");
                }
            } catch (DaoException e) {
                throw new ServiceException("Could not get the list of logs", e);
            }
        }
        try {
            logDao.writeResultToFile(convertToResultList(logRecords));
        } catch (DaoException e) {
            throw new ServiceException("Could not write the list of logs by username. Please check input parameters", e);
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
