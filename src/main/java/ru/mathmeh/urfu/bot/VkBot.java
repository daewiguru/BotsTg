package ru.mathmeh.urfu.bot;

import api.longpoll.bots.LongPollBot;
import api.longpoll.bots.exceptions.VkApiException;
import api.longpoll.bots.model.events.messages.MessageNew;
import api.longpoll.bots.model.objects.basic.Message;
import ru.mathmeh.urfu.bot.Logic.Config;
import ru.mathmeh.urfu.bot.Logic.Logic;

public class VkBot extends LongPollBot implements Bot{
    private final Config config = new Config();
    /**
     * Hidden logic
     */
    private final Logic logic;
    /**
     * Constructor of Telegram Bot class
     */
    public VkBot() {
        this.logic = new Logic();
    }
    /**
     * Handler for new messages.
     *
     * @param messageNew Object containing information about the new message.
     */
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
    /**
     * This method gives us bot token which is read from config
     * @return bot token
     */
    @Override
    public String getAccessToken() {
        return config.getVkToken();
    }

    @Override
    public void sendMessage(Long id, String message) {

    }
}
