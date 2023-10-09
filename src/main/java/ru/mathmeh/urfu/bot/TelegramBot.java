package ru.mathmeh.urfu.bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;





public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramMessageHandler messageHandler;

    
    public TelegramBot(){
        messageHandler = new TelegramMessageHandler();
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
        try {
            execute(messageHandler.parse(update.getMessage()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}

