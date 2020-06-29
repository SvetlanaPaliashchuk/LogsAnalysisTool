package by.yuntsevich.app.service;

import java.util.List;

public interface LogGrouper {
    List<String> groupLogsByUserName (String fileName) throws ServiceException;
    List<String> groupLogsByTimeUnit(String fileName, String timeUnit) throws ServiceException;

}
