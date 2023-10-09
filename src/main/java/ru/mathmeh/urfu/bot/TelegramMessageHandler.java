package ru.mathmeh.urfu.bot;



import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
/**
 *class TelegramMessageHandler
 * @author lendys(Yaroslav Prisepnyj)
 * @version 1.0
 * parsing message
 */
public class TelegramMessageHandler {
    public SendMessage parse(Message message){
        SendMessage.SendMessageBuilder sendMessageBuilder = SendMessage.builder()
                .chatId(message.getFrom().getId());
        switch (message.getText()) {
            case "/start" -> sendMessageBuilder.text("Hello, im new bots");
            case "/help" -> sendMessageBuilder.text("I'm a bot that mirrors a message.");
            default -> sendMessageBuilder.text(message.getText());
        }
        return sendMessageBuilder.build();
    }


}
