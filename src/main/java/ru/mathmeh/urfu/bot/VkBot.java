package ru.mathmeh.urfu.bot;

import api.longpoll.bots.LongPollBot;
import api.longpoll.bots.exceptions.VkApiException;
import api.longpoll.bots.model.events.messages.MessageNew;
import api.longpoll.bots.model.objects.basic.Message;
import ru.mathmeh.urfu.bot.Logic.Config;
import ru.mathmeh.urfu.bot.Logic.Logic;

public class VkBot extends LongPollBot {
    private final Config config = new Config();
    private final Logic logic;

    public VkBot() {
        this.logic = new Logic();
    }

    @Override
    public void onMessageNew(MessageNew messageNew) {
        try {
            Message message = messageNew.getMessage();
            if (message.hasText()) {
                String responce =  logic.handleMessage(message.getText());
                vk.messages.send()
                        .setPeerId(message.getPeerId())
                        .setMessage(responce)
                        .execute();
            }
        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getAccessToken() {
        return config.getVkToken();
    }
}
