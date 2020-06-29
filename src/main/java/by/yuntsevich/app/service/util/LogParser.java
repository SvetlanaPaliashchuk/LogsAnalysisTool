package by.yuntsevich.app.service.util;

import by.yuntsevich.app.entity.LogRecord;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogParser {
    private static final String DELIMITER = ";";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public LocalDateTime parseDateTime(String date){
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

    public List<LogRecord> createLogRecordList (List<String> logsList){
        List<LogRecord> logRecordList = new ArrayList<>();
        for(String line : logsList){
            LogRecord logRecord = buildLogRecord(splitLine(line));
            logRecordList.add(logRecord);
        }
        return logRecordList;
    }

    private String[] splitLine(String line){
        return line.split(DELIMITER);
    }

    private LogRecord buildLogRecord (String[] parts){
        LogRecord logRecord = new LogRecord();
        logRecord.setDateTime(parseDateTime(parts[0]));
        logRecord.setUserName(parts[1]);
        logRecord.setMessage(parts[2]);
        return logRecord;
    }
}
