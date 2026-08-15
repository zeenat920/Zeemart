package com.zeenat.zeemart.util;

import javax.sql.DataSource;

public final class DataSourceManager {
    private static volatile DataSource dataSource;

    private DataSourceManager() {}

    public static void set(DataSource ds) {
        dataSource = ds;
    }

    public static DataSource get() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource not initialized - AppContextListener did not run");
        }
        return dataSource;
    }
}
