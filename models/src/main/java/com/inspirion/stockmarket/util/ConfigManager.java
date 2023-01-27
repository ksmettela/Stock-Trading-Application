package com.inspirion.stockmarket.util;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    static ConfigManager manager;

    public Configuration getConfiguration() {
        return configuration;
    }

    Configuration configuration;

    ConfigManager() throws IOException {
        readConfig();
    }

    private void readConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File("./config.json");
        configuration = mapper.readValue(file, Configuration.class);
    }

    public static ConfigManager getInstance() {
        if (null == manager) {
            try {
                manager = new ConfigManager();
            } catch (Exception e) {
            }
        }
        return manager;
    }
}
