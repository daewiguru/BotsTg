package ru.mathmeh.urfu.bot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mathmeh.urfu.bot.Logic.Config;
import ru.mathmeh.urfu.bot.Logic.Logic;

import java.util.ArrayList;
import java.util.List;
/**
 * This is realization of Telegram Bot
 */
public class TelegramBot extends TelegramLongPollingBot implements Bot {

    private final Logic logic;
    private final Config config = new Config();
    /**
     * Constructor of Telegram Bot class
     */
    public TelegramBot() {
        logic = new Logic();
    }
    /**
     * This method gives us bot name which is read from config
     * @return bot name
     */
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
    /**
     * This method gives us bot token which is read from config
     * @return bot token
     */
    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    /**
     * This is a telegram bot method which react on new messages
     * @param update This is a telegram API object that helps us interact with chat events
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();
            String userMessage = message.getText();

            String response = logic.handleMessage(userMessage);
            sendStartMessageWithButtons(chatId, response);
        }
    }

    /**
     * Sends a message with text and a keyboard containing buttons to initiate interaction.
     *
     * @param chatId    The identifier of the chat where the message will be sent.
     * @param response  The text of the message to be sent.
     */
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

    /**
     * This method creates a message and send it to user
     * @param id chatId or userId
     * @param message text of send message
     */
    @Override
    public void sendMessage(Long id, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(id.toString())
                .text(message)
                .build();

        try {
            execute(sendMessage).getMessageId();
        } catch (TelegramApiException e) {
            System.err.println(e);
        }
    }
}
