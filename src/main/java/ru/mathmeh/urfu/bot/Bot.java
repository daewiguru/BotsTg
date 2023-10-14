package ru.mathmeh.urfu.bot;

/**
 * This is an interface for all bots realization
 */
public interface Bot {
    /**
     * This method will send messages to user
     * @param id chatId or userId
     * @param message text of send message
     */
    // There is no more methods because set methods are useless now
    // and receiveMessage method will have platform-depend realization
    void sendMessage(Long id, String message);
}


