package com.telegramBot.handler;

import com.telegramBot.bot.Bot;
import com.telegramBot.bot.Buttons;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CategoriesHandler extends AbstractHandler  {
    private static final Logger log = Logger.getLogger(CategoriesHandler.class);
    private final String END_LINE = "\n";

    public CategoriesHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(Long chatId, Update update) {

        SendMessage sendMessage = Bot.doSendMsg(chatId,"categoriesMsg");
        Buttons.setInlineKeyBoardCategories(sendMessage, chatId);

        bot.sendQueue.add(sendMessage);
        return "";
    }
}
