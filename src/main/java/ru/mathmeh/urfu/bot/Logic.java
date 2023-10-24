package ru.mathmeh.urfu.bot;

/**
 * This class implements the bot logic
 * @author lendys(Yaroslav Prisepnyj)
 * @version 1.0
 */
public class Logic {

    public Logic(){

    }

    /**
     * This method realize cross-platform logic of bots
     * @param message text of user`s message
     * @return tet of bot message
     */
    public String handleMessage(String message){
        switch (message){
            case "/start" -> {
                return "Hello, im new bots";
            }
            case "/help" -> {
                return "I'm a bot that mirrors a message.";
            }
            default -> {
                return message;
            }
        }
    }
}
