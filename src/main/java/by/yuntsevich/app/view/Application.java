package by.yuntsevich.app.view;

import by.yuntsevich.app.controller.Controller;
import by.yuntsevich.app.dao.DaoException;
import by.yuntsevich.app.service.ServiceException;

public class Application {
    public static void main(String[] args) throws DaoException, ServiceException {
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
        System.out.println(con.executeTask("GROUP_LOGS_BY_TIME_UNIT,dir,YEAR"));
    }
}
