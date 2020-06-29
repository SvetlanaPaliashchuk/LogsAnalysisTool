package by.yuntsevich.app.dao.impl;

import by.yuntsevich.app.dao.DAOException;
import by.yuntsevich.app.dao.LogDao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogDaoImpl implements LogDao {
    private static final String LOGS_DIRECTORY = "logs/";
    private static final String OUTPUT_FILE_PATH = "output/result.csv";

    @Override
    public List<String> getLogsFromFile(String fileName) throws DAOException {
        List<String> logsList;
        try {
            logsList = Files.lines(Paths.get(LOGS_DIRECTORY + fileName))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new DAOException(e);
        }
        return logsList;
    }

    @Override
    public List<String> getLogsFromDirectory() throws DAOException {
        List<String> logsList = new ArrayList<>();
        List<String> filesInDirectory;
        try {
            filesInDirectory = Files.walk(Paths.get(LOGS_DIRECTORY))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
            for (String s : filesInDirectory) {
                List<String> logsListInFile = Files.lines(Paths.get(s))
                        .collect(Collectors.toList());
                logsList.addAll(logsListInFile);
            }
        } catch (IOException e) {
            throw new DAOException(e);
        }
        return logsList;
    }

    @Override
    public void writeResultToFile(List<String> result) throws DAOException {
        try {
            if (Files.notExists(Paths.get(OUTPUT_FILE_PATH))) {
                Files.createFile(Paths.get(OUTPUT_FILE_PATH));
            }

        Files.write(Paths.get(OUTPUT_FILE_PATH), result, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

}
