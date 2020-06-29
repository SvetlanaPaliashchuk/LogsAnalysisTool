package by.yuntsevich.app.dao;

import java.util.List;

public interface LogDao {
    List<String> getLogsFromFile(String fileName) throws DaoException;
    List<String> getLogsFromDirectory() throws DaoException;
    void writeResultToFile(List<String> result) throws DaoException;


}
