package com.telegramBot.handler;


import com.telegramBot.bot.Bot;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractHandler {
    Bot bot;

    AbstractHandler(Bot bot) {
        this.bot = bot;
    }

    public abstract String operate(Long chatId, Update update);
}
