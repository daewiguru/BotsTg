package ru.mathmeh.urfu.bot.Logic;

/**
 * A config class for the bot (and in the future, the database)
 * @author arsbars24 (Арсений)
 */

public class Config {
    //public final String CONFIGURATION_BOT_FILE = "config/config.properties";
    //TODO create a database, and link it with bot
    //public static final String CONFIGURATION_DB_FILE = " ";
    /**
     * A constructor for the Config class.
     */
    public Config() {
        load();
    }

    /**
     * Constant fields of the class are strings that contain data from the config and are used to run the bot
     */
    private String vkToken;
    private String botName;
    private String botToken;

    public String getBotName() {
        return botName;
    }
    public String getBotToken() {
        return botToken;
    }
    public String getVkToken(){
        return vkToken;
    }

    //TODO: link database to bot
    //public static String DB_URL;
    // public static String DB_USER;
    // public static String DB_PASSWORD;

    /**
     *A method for loading data from a bot config that uses the properties library
     *
     */

    public void load() {
        /*Properties botSettings = new Properties();
        try (FileInputStream is = new FileInputStream(CONFIGURATION_BOT_FILE)){
            botSettings.load(is);
            is.close();
            System.out.print("Config was loaded successfully");
        } catch (Exception e){
            System.out.print("Config was not load");
        }

         */
        vkToken = System.getenv("VK_BOT_TOKEN");
        botName = System.getenv("BOT_NAME");
        botToken = System.getenv("TG_BOT_TOKEN");
    }

}