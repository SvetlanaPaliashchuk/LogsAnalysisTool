package by.yuntsevich.app.dao;

import by.yuntsevich.app.dao.impl.LogDaoImpl;

public class DaoFactory {
    private static final DaoFactory INSTANCE = new DaoFactory();

    private static final LogDao LOG_DAO_INSTANCE = new LogDaoImpl();

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public LogDao getLogDao() {
        return LOG_DAO_INSTANCE;
    }

}
