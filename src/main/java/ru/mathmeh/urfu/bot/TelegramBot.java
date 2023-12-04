package ru.mathmeh.urfu.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.mathmeh.urfu.bot.Logic.*;

import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {
    private static TelegramBot instance;

    private final Logic logic;
    private final Config config;

    private TelegramBot() {
        logic = new Logic();
        config = new Config();
    }

    public static TelegramBot getInstance() {
        if (instance == null) {
            instance = new TelegramBot();
        }
        return instance;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String userMessage = update.getMessage().getText();

            String response = logic.handleMessage(userMessage);
            sendStartMessageWithButtons(chatId, response);
        }
    }

    public void sendReminderMessage(long chatId, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(message)
                .build();

        try {
            execute(sendMessage).getMessageId();
        } catch (TelegramApiException e) {
            System.err.println(e);
        }
    }

    private void sendStartMessageWithButtons(long chatId, String response) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(response);

        List<KeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(new KeyboardButton("/help"));
        keyboardButtonsRow.add(new KeyboardButton("/list_categories"));
        keyboardButtonsRow.add(new KeyboardButton("/table"));

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(keyboardButtonsRow);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
