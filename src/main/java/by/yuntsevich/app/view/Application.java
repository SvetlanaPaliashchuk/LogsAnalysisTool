package by.yuntsevich.app.view;

import by.yuntsevich.app.controller.Controller;
import by.yuntsevich.app.dao.DAOException;
import by.yuntsevich.app.dao.impl.LogDaoImpl;
import by.yuntsevich.app.entity.LogRecord;
import by.yuntsevich.app.service.ServiceException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws DAOException, ServiceException {
//        LogDaoImpl ldi = new LogDaoImpl();
//        List<String> list = ldi.getLogsFromFile();
//
//       list.forEach(System.out::println);


//        LocalDateTime ldt = LocalDateTime.now();
//        LogRecord lr = new LogRecord(ldt, "user1", "message");
//        System.out.println(lr);


//        List<LogRecord> list = new ArrayList<LogRecord>();
//        long recordsTotal = list.stream()
//                .filter(record -> record.getUserName() == "user1")
//                .count();
   //     long recordsTotal2 = list.stream();


        Controller con = new Controller();
        System.out.println(con.executeTask("GET_LOGS_BY_TIME_PERIOD,,2020-01-01 00:00:00,2020-06-28 00:00:00"));
    }
}
