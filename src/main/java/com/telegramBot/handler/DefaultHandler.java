package com.telegramBot.handler;


import com.telegramBot.bot.Bot;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;


public class DefaultHandler extends AbstractHandler {
    private static final Logger log = Logger.getLogger(DefaultHandler.class);

    public DefaultHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(Long chatId,  Update update) {
        return "";
    }
}
