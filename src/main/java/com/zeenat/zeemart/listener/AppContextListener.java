package com.zeenat.zeemart.listener;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zeenat.zeemart.util.DataSourceManager;

import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(AppContextListener.class.getName());
    private HikariDataSource ds;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            props.load(in);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setDriverClassName(props.getProperty("db.driver"));
            config.setUsername(props.getProperty("db.user"));
            config.setPassword(props.getProperty("db.password"));
            config.setMaximumPoolSize(10);

            ds = new HikariDataSource(config);
            DataSourceManager.set(ds);

            runScript("schema.sql");
            runScript("seed.sql");

            LOG.info("ZeeMart datasource initialized and schema applied");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize datasource", e);
        }
    }

    private void runScript(String resourceName) throws Exception {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) return;
            String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            try (Connection conn = ds.getConnection();
                 Statement stmt = conn.createStatement()) {
                for (String part : sql.split(";")) {
                    String trimmed = part.trim();
                    if (!trimmed.isEmpty()) {
                        stmt.execute(trimmed);
                    }
                }
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (ds != null) ds.close();
    }
}
