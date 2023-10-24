package ru.mathmeh.urfu.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * This is realization of Telegram Bot
 */
public class TelegramBot extends TelegramLongPollingBot implements Bot{

    /**
     * Hidden logic
     */
    private final Logic logic;
    private final Config config;
    /**
     * Constructor of Telegram Bot class
     */
    public TelegramBot(){
        logic = new Logic();
        config = new Config();
    }

    /**
     * This method gives us bot name which is read from config
     * @return bot name
     */
    @Override
    public String getBotUsername() {
        return config.botName;
    }
  
    /**
     * This method gives us bot token which is read from config
     * @return bot token
     */

    @Override
    public String getBotToken() {
        return config.botToken;
    }


    /**
     * This is a telegram bot method which react on new messages
     * @param update This is a telegram API object that helps us interact with chat events
     */

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message m = update.getMessage();
            if (m.hasText()) {
                String response = logic.handleMessage(m.getText());
                sendMessage(m.getChatId(), response);
            }
        }
    }


    /**
     * This method creates a message and send it to user
     * @param id chatId or userId
     * @param message text of send message
     */

    @Override
    public void sendMessage(Long id, String message) {
        SendMessage msg = SendMessage.builder()
                .chatId(id.toString())
                .text(message)
                .build();

        try {
            execute(msg).getMessageId();
        } catch (TelegramApiException e) {
            System.err.println(e);
        }
    }
}

