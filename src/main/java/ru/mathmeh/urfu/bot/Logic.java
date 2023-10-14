package ru.mathmeh.urfu.bot;

/**
 *class TelegramMessageHandler
 * @author lendys(Yaroslav Prisepnyj)
 * @version 1.0
 * parsing message
 */
public class Logic {
    public Logic(){

    }
    public String messageHandler(String message){
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
