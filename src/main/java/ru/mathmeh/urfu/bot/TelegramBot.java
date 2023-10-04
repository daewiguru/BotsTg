package ru.mathmeh.urfu.bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "manageres_bot";
    }

    @Override
    public String getBotToken() {
        return "6455855625:AAFysNBWK5hwTxh4BVuP7CKtBf5BFGD2T20";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();

        // help command answer
        SendMessage HelpMessage = new SendMessage(chatId, "Привет! Я бот, который поможет тебе в чем-то.");
        // start command answer
        SendMessage StartMessage = new SendMessage(chatId, "Начинаем работу");
        if ("/start".equals(text)){
            try {
                this.execute(StartMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if ("/help".equals(text)) {
            try {
                this.execute(HelpMessage);
            } catch (TelegramApiException e){
                throw new RuntimeException(e);
            }
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(text);

            try {
                this.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }




    }
}
