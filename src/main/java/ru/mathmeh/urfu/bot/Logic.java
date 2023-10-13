package ru.mathmeh.urfu.bot;

/**
 * This is an abstract class of bot logic. It embodies bot interface.
 * @version 1.0
 * @author Арсений
 */
public abstract class Logic {
    private final Bot bot;
    private String botToken;
    private String botName;

    /**
     * This is a constructor for Logic class.
     * @param bot The bot instance that implements the Bot interface
     */
    public Logic(Bot bot){
        this.bot = bot;

    }

    /**
     * This method set bot token.
     * @param token Is a platform - depend token
     */
    protected void setBotToken(String token) {
        this.botToken = token;
    }

    /**
     * This method set bot name.
     * @param name Is a unique name for all platforms
     */
    protected void setBotName(String name) {
        this.botName = name;
    }

    /**
     * This is an abstract method that receive user`s message.
     * Realization of method is platform - dependent.
     * @param userId Identification of user and chat with it
     * @param message Text of message
     */
    public abstract void receiveMessage(String userId, String message);

    /**
     * This is abstract method that send messages to user.
     * @param userId Identification of user and chat with it
     * @param message Text of message
     */
    public abstract void sendMessage(String userId, String message);

    /**
     * This method gives a value of field
     * @return bot
     */
    public Bot getBot() {
        return bot;
    }
    /**
     * This method gives a value of field
     * @return bot token
     */
    public String getBotToken() {
        return botToken;
    }
    /**
     * This method gives a value of field
     * @return bot name
     */
    public String getBotName() {
        return botName;
    }
}
