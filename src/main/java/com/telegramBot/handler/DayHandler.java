package com.telegramBot.handler;

import com.telegramBot.bot.Bot;
import com.telegramBot.bot.Buttons;
import com.telegramBot.dbWork.heap.HeapWork;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

public class DayHandler extends AbstractHandler  {
    private static final Logger log = Logger.getLogger(DayHandler.class);
    private final String END_LINE = "\n";

    public DayHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(Long chatId, Update update) {

        SendMessage sendMessage = Bot.doSendMsg(chatId,"dayHandlerMsg1");
        Buttons.setButtonsMain(sendMessage, chatId);

        StringBuilder s = new StringBuilder();

        ArrayList arrayList = HeapWork.getDayStatSpans(chatId);
        for (Object string: arrayList ) {
            s.append(string).append("\n");
        }

        SendMessage sendMessage1 = new SendMessage();
        sendMessage1.setChatId(chatId).setText(s.toString());

        bot.sendQueue.add(sendMessage);
        bot.sendQueue.add(sendMessage1);
        return "";
    }
}
