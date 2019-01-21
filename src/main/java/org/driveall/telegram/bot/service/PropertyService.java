package org.driveall.telegram.bot.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyService {
    private static final Properties props;

    static {
        props = new Properties();
        try (InputStream input = new FileInputStream("src\\main\\resources\\application.properties")) {
            props.load(input);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Properties getProps() {
        return props;
    }
}
