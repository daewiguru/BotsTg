package ru.mathmeh.urfu.bot;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

class TelegramMessageHandlerTest {
    @Test //Тест для команды /start
    public void parseTestStart(){
        Message msg = new Message();
        msg.setText("/start");
        User user = new User();
        user.setUserName("testUser");
        user.setId(0L);
        msg.setFrom(user);
        TelegramMessageHandler mh = new TelegramMessageHandler();
        SendMessage snm = mh.parse(msg);
        Assertions.assertEquals("Hello, im new bots", snm.getText());

    }
    @Test //Тест для команды /help
    public void parseTestHelp(){
        Message msg = new Message();
        msg.setText("/help");
        User user = new User();
        user.setUserName("testUser");
        user.setId(0L);
        msg.setFrom(user);
        TelegramMessageHandler mh = new TelegramMessageHandler();
        SendMessage snm = mh.parse(msg);
        Assertions.assertEquals("I'm a bot that mirrors a message.", snm.getText());
    }

}