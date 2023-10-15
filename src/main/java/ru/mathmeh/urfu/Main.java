package ru.mathmeh.urfu;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.mathmeh.urfu.bot.Config;
import ru.mathmeh.urfu.bot.TelegramBot;

/**
 * Start point of app
 */
public class Main {
    public static void main(String[] args) {
        try {
            Config.load();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}