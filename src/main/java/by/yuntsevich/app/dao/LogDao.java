package by.yuntsevich.app.dao;

import java.util.List;

public interface LogDao {
    List<String> getLogsFromFile(String fileName) throws DAOException;
    List<String> getLogsFromDirectory() throws DAOException;
    void writeResultToFile(List<String> result) throws DAOException;


}
