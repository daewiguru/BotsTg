package ru.mathmeh.urfu.bot;

/**
 * This is an interface for all platforms bots.
 * @author Арсений
 */

public interface Bot {

    /**
     * This function will set botToken.
     * @param token botToken (platform - depend)
     */
    void setBotToken(String token);
    /**
     * This function will set botName.
     * @param name botName (unique)
     */
    void setBotName(String name);

    /**
     * This abstract function is used for receiving messages from users.
     * @param userId - user identifier
     * @param message - text of user`s message
     */
    void receiveMessage(String userId, String message);

    /**
     * This abstract function is used for sending messages to users.
     * @param userId - user identifier
     * @param message - text of user`s message
     */
    void sendMessage(String userId, String message);
}
