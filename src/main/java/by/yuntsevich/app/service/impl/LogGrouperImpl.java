package by.yuntsevich.app.service.impl;

import by.yuntsevich.app.dao.DaoException;
import by.yuntsevich.app.dao.DaoFactory;
import by.yuntsevich.app.dao.LogDao;
import by.yuntsevich.app.entity.LogRecord;
import by.yuntsevich.app.service.LogGrouper;
import by.yuntsevich.app.service.LogService;
import by.yuntsevich.app.service.ServiceException;
import by.yuntsevich.app.service.ServiceFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogGrouperImpl  implements LogGrouper {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private LogDao logDao = daoFactory.getLogDao();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();

    @Override
    public List<String> groupLogsByUserName(String fileName) throws ServiceException {
        LogService service = serviceFactory.getLogService();
        Map<String, Long> resultMap = service.getLogsList(fileName).stream()
                .collect(Collectors.groupingBy(LogRecord::getUserName, Collectors.counting()));
        List<String> result = new ArrayList<>();

        for (Map.Entry entry : resultMap.entrySet()) {
            result.add(entry.getKey().toString() + "      " + entry.getValue().toString());
        }
        result.add("----------------------------------------------");

        try {
            logDao.writeResultToFile(result);
        } catch (DaoException e) {
            throw new ServiceException("Could not write the list of logs", e);
        }
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
        } catch (DaoException e) {
            throw new ServiceException("Could not write the list of logs", e);
        }
        return result;
    }

    private List<LogRecord> setTruncatedDate(String fileName, String timeUnit) throws ServiceException {
        LogService service = serviceFactory.getLogService();
        LocalDateTime date;
        List<LogRecord> logsList = service.getLogsList(fileName);

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
