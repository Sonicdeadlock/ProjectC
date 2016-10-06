package io.sonicdeadlock.projectc.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Alex on 10/5/2016.
 */
public class PropertiesLoader {
    private static final Properties PROPERTIES;
    static {
        PROPERTIES = new Properties();
        try {
            PROPERTIES.load(PropertiesLoader.class.getClassLoader().getResourceAsStream("projectC.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String propertyName){
        return  PROPERTIES.getProperty(propertyName);
    }

    public static String getProperty(String dir,String propertyName){
        return PROPERTIES.getProperty(dir+"."+propertyName);
    }
}
