package by.yuntsevich.app.view.menu;

import by.yuntsevich.app.service.util.DataScanner;
import by.yuntsevich.app.controller.Controller;
import by.yuntsevich.app.controller.command.CommandName;

public class Menu {
    private Controller controller = new Controller();
    private String fileName;
    private int actionNumber;

    public void start() {
        do {
            System.out.println("Please choose the action:\n" +
                    "Press 1 to get logs by username\n" +
                    "Press 2 to get logs by time period\n" +
                    "Press 3 to get logs by message pattern\n" +
                    "Press 4 to get grouped logs by username\n" +
                    "Press 5 to get grouped logs by time unit\n" +
                    "Press 6 to get all logs\n" +
                    "Press 0 to exit");

            actionNumber = DataScanner.enterIntFromConsole();
            chooseCommand(actionNumber);
        }
        while (actionNumber != 0);
    }

    private String askToEnterFileName() {
        System.out.println("Please enter filename:");
        fileName = DataScanner.enterLineFromConsole();
        if (fileName.equals("")) {
            fileName = "dir";
        }
        return fileName;
    }

    private void chooseCommand(int actionNumber) {
        switch (actionNumber) {
            case 1:
                fileName = askToEnterFileName();
                System.out.println("Please enter username:");
                String userName = DataScanner.enterLineFromConsole();
                System.out.println(controller
                        .executeTask(CommandName.GET_LOGS_BY_USERNAME
                                + "," + fileName + "," + userName));
                break;
            case 2:
                fileName = askToEnterFileName();
                System.out.println("Please enter start date in format yyyy-MM-dd HH:mm:ss:");
                String startDate = DataScanner.enterLineFromConsole();
                System.out.println("Please enter end date in format yyyy-MM-dd HH:mm:ss:");
                String endDate = DataScanner.enterLineFromConsole();
                System.out.println(controller
                        .executeTask(CommandName.GET_LOGS_BY_TIME_PERIOD
                                + "," + fileName + "," + startDate + "," + endDate));
                break;
            case 3:
                fileName = askToEnterFileName();
                System.out.println("Please enter message pattern:");
                String pattern = DataScanner.enterLineFromConsole();
                System.out.println(controller
                        .executeTask(CommandName.GET_LOGS_BY_MESSAGE_PATTERN
                                + "," + fileName + "," + pattern));
                break;
            case 4:
                fileName = askToEnterFileName();
                System.out.println(controller
                        .executeTask(CommandName.GROUP_LOGS_BY_USERNAME
                                + "," + fileName));
                break;
            case 5:
                fileName = askToEnterFileName();
                System.out.println("Please enter time unit (HOUR, DAY, MONTH, YEAR):");
                String timeUnit = DataScanner.enterLineFromConsole();
                System.out.println(controller
                        .executeTask(CommandName.GROUP_LOGS_BY_TIME_UNIT
                                + "," + fileName + "," + timeUnit));
                break;
            case 6:
                fileName = askToEnterFileName();
                System.out.println(controller.executeTask(CommandName.GET_ALL_LOGS.toString() + "," + fileName));
                break;
        }
    }
}
