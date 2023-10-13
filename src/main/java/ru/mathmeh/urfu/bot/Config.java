package ru.mathmeh.urfu.bot;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * A config class for the bot (and in the future, the database)
 * @author arsbars24 (Арсений)
 */

public class Config {
    public static final String CONFIGURATION_BOT_FILE = "config/config.properties";
    //TODO create a database, and link it with bot
    //public static final String CONFIGURATION_DB_FILE = " ";

    /**
     * Constant fields of the class are strings that contain data from the config and are used to run the bot
     */
    public static String BOT_NAME;
    public static String BOT_TOKEN;

    //TODO: link database to bot
    /*public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASSWORD;*/

    /**
     *A method for loading data from a bot config that uses the properties library
     *
     */

    public static void load() {
        Properties botSettings = new Properties();
        try (FileInputStream is = new FileInputStream(CONFIGURATION_BOT_FILE)){
            botSettings.load(is);
            is.close();
            System.out.print("Config was loaded successfully");
        } catch (Exception e){
            System.out.print("Config was not load");
        }
        BOT_NAME = botSettings.getProperty("BotName", " ");// TODO fill form
        BOT_TOKEN = botSettings.getProperty("BotToken", " ");
    }




}
