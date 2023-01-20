package com.travel_agency.controller.listeners;

import com.travel_agency.controller.CommandFactory;
import com.travel_agency.model.DB.DBManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        logger.info("Context destroyed");
    }

    private void initCommandFactory() {
        CommandFactory.getInstance();
        logger.info("Command factory initialized");
    }

    private void initDBManager() {
        DBManager.getInstance();
        logger.info("DB manager initialized");
    }
}
