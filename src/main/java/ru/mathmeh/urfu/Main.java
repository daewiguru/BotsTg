package ru.mathmeh.urfu;

import api.longpoll.bots.exceptions.VkApiException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.mathmeh.urfu.bot.TelegramBot;
import ru.mathmeh.urfu.bot.VkBot;

/**
 * Start point of app
 */
public class Main {
    public static void main(String[] args) throws VkApiException {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        new VkBot().startPolling();
    }
}