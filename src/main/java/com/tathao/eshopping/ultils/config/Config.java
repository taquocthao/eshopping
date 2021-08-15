package com.tathao.eshopping.ultils.config;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;
import java.util.Set;

public class Config extends Properties {

    private static final Logger logger = Logger.getLogger(Config.class);

    private static Config ourInstance = new Config();
    public static Config getInstance() {
        return ourInstance;
    }

    private Config() {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        if(inputStream != null) {
            try {
                properties.load(inputStream);
                Set<String> propertyNames = properties.stringPropertyNames();
                for (String key: propertyNames) {
                    this.put(key,properties.get(key));
                }
            } catch (Exception e) {
                logger.error("Could not load file from server configuration. Please check it");
            }
        } else {
            logger.error("Could not load file from server configuration. Please check it");
        }
    }
}
