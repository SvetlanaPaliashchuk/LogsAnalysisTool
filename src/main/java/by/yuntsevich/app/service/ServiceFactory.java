package by.yuntsevich.app.service;

import by.yuntsevich.app.service.impl.LogServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private static final LogService LOG_SERVICE_INSTANCE = new LogServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public LogService getLogService() {
        return LOG_SERVICE_INSTANCE;
    }

}
