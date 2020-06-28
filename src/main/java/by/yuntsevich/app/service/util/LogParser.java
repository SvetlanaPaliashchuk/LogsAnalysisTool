package by.yuntsevich.app.service.util;

import by.yuntsevich.app.entity.LogRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogParser {
    public static final String DELIMITER = ";";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    public DateTimeFormatter getDateFormatter() {
        return dateFormatter;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public LocalDate parseDate(String date){
        return LocalDate.parse(date, dateFormatter);
    }

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
        //System.out.println(parts[0]);

        logRecord.setDateTime(parseDateTime(parts[0]));
        logRecord.setUserName(parts[1]);
        logRecord.setMessage(parts[2]);
        return logRecord;
    }
}
