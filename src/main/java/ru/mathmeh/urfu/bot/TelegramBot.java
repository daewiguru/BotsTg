package ru.mathmeh.urfu.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
public class TelegramBot extends TelegramLongPollingBot implements Bot{
    private final Logic telegramLogic;
    /**
     * Initialization bot session, where config class fields are used
     */
    public TelegramBot(){
        telegramLogic= new Logic();
    }
    @Override
    public String getBotUsername() {
        return Config.BOT_NAME;
    }
    @Override
    public String getBotToken() {
        return Config.BOT_TOKEN;
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message m = update.getMessage();
            if (m.hasText()) {
                String response = telegramLogic.messageHandler(m.getText());
                sendMessage(m.getChatId(), response);
            }
        }
    }
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

