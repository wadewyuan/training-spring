package com.wadeyuan.training.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    
    private static Properties props = null;
    
    static {
        InputStream inStream = PropertyUtil.class.getClassLoader().getResourceAsStream("app.properties");
        props = new Properties();
        try {
            props.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String get(String key) {
        return props.getProperty(key);
    }
}
