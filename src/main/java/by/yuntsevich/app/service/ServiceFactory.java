package by.yuntsevich.app.service;

import by.yuntsevich.app.service.impl.LogFilterImpl;
import by.yuntsevich.app.service.impl.LogGrouperImpl;
import by.yuntsevich.app.service.impl.LogServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private static final LogService LOG_SERVICE_INSTANCE = new LogServiceImpl();
    private static final LogFilter LOG_FILTER_INSTANCE = new LogFilterImpl();
    private static final LogGrouper LOG_GROUPER_INSTANCE = new LogGrouperImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public LogService getLogService() {
        return LOG_SERVICE_INSTANCE;
    }
    public LogFilter getLogFilter() {
        return LOG_FILTER_INSTANCE;
    }
    public LogGrouper getLogGrouper() {
        return LOG_GROUPER_INSTANCE;
    }

}
