package com.telegramBot.handler;

import com.telegramBot.bot.Bot;
import com.telegramBot.bot.Buttons;
import com.telegramBot.dbWork.users.User;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ChangeMasterLimitHandler   {
    private static final Logger log = Logger.getLogger(ChangeMasterLimitHandler.class);
    private final String END_LINE = "\n";
    private Bot bot;
    public ChangeMasterLimitHandler(Bot bot) {
        this.bot = bot;
    }

    public String operate(Long chatId, String master ) {

        StringBuilder s = new StringBuilder();
        String lang = User.getLanguage(chatId);
        s.append(Bot.getFastMsg(lang,"masterLim")).append(master);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId).setText(s.toString());
        Buttons.setButtonsMain(sendMessage, chatId);

        bot.sendQueue.add(sendMessage);

        return "";
    }
}
