package ru.mathmeh.urfu.bot;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Class for work with config
 */

public class Config {
    public static final String CONFIGURATION_BOT_FILE = "config/config.properties";
    //TODO create a database, and link it with bot
    //public static final String CONFIGURATION_DB_FILE = " ";
    public static String BOT_NAME;
    public static String BOT_TOKEN;

    /*public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASSWORD;*/

    public static void load() {
        Properties botSettings = new Properties();
        try (FileInputStream is = new FileInputStream(new File(CONFIGURATION_BOT_FILE))){
            botSettings.load(is);
            is.close();
            System.out.print("Config was loaded successfully");
        } catch (Exception e){
            System.out.print("Config was not load");
        }
        BOT_NAME = botSettings.getProperty("BotName", " ");// TODO fill form
        BOT_TOKEN = botSettings.getProperty("BotToken", " ");
    }
    ;



}
