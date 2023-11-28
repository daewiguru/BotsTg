package ru.mathmeh.urfu.bot;

import api.longpoll.bots.LongPollBot;
import api.longpoll.bots.exceptions.VkApiException;
import api.longpoll.bots.model.events.messages.MessageNew;
import api.longpoll.bots.model.objects.basic.Message;
import ru.mathmeh.urfu.bot.Logic.Logic;

public class VkBot extends LongPollBot {

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
        return "vk1.a.fwS1RMfwMTKB0L0557S374kSibV_RTjchHykwzlQzlRLaUfno_RUM6eBHXjGORXd1V3y4ow2xB8n9JgNoqE36gab2ixPvdDmtDAH-_H_vnj_mEha5OyXudVi-DOfJxszCM_T1FvgDNP9laQfbTd5irQAVEDK8uMRbG80eWJmBMagEGPZatkFI6xA1azEqAEYAwz-pGq2Fa09v-eF7weAUQ";
    }
}
