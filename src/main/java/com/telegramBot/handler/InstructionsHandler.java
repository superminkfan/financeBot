package com.telegramBot.handler;

import com.telegramBot.bot.Bot;
import com.telegramBot.bot.Buttons;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class InstructionsHandler  extends AbstractHandler  {
    private static final Logger log = Logger.getLogger(InstructionsHandler.class);
    private final String END_LINE = "\n";

    public InstructionsHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(Long chatId, Update update) {

        SendMessage sendMessage = Bot.doSendMsg(chatId,"instructionMsg");
        Buttons.setButtonsMain(sendMessage, chatId);

        bot.sendQueue.add(sendMessage);
        return "";
    }

}
