package com.travel_agency.controller.listeners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Context initialized");
        initDBManager();
        initCommandFactory();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        deregisterDrivers();
        shutdownCleanupThread();

        logger.info("Context destroyed");
    }

    private static void shutdownCleanupThread() {
        //shutdown connection cleanup thread
        try {
            AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Exception e) {
            logger.error("Exception while cleanup thread shutdown", e);
        }
        logger.info("Cleanup thread shutdowned");
    }

    private static void deregisterDrivers() {
        // This manually deregisters JDBC driver, which prevents complaining about memory leaks
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                logger.warn(String.format("Error deregistering driver %s", driver), e);
            }
        }
    }

    private void initCommandFactory() {
        try {
            Class.forName("com.travel_agency.controller.CommandFactory");
        } catch (ClassNotFoundException e) {
            logger.error("Command factory isn`t initialized", e);
        }
        logger.info("Command factory initialized");
    }

    private void initDBManager() {
        try {
            Class.forName("com.travel_agency.model.DB.DBManager");
        } catch (ClassNotFoundException e) {
            logger.error("DBManager isn`t initialized", e);
        }
        logger.info("DB manager initialized");
    }
}
