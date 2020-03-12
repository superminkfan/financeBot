package com.telegramBot.handler;

import com.telegramBot.bot.Bot;
import com.telegramBot.bot.Buttons;
import com.telegramBot.dbWork.heap.HeapWork;
import com.telegramBot.dbWork.users.User;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class MyFinanceHandler extends AbstractHandler  {
    private static final Logger log = Logger.getLogger(MyFinanceHandler.class);
    private final String END_LINE = "\n";

    public MyFinanceHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(Long chatId, Update update) {

        SendMessage sendMessage = new SendMessage();

        String lang = User.getLanguage(chatId);
        StringBuilder s = new StringBuilder();

        HashMap hashMap = User.getBudget(chatId);
        HashMap hashMap1 = HeapWork.getStat(chatId,1);

        s.append(Bot.getFastMsg(lang,"financeMsg1"));
        s.append(Bot.getFastMsg(lang,"financeMsg2"));
        s.append(hashMap.get("day")).append("\n");
        s.append(Bot.getFastMsg(lang,"financeMsg3"));
        s.append(hashMap.get("mounth")).append("\n");
        s.append(Bot.getFastMsg(lang,"financeMsg4"));
        s.append(hashMap.get("year")).append("\n\n");
        s.append(Bot.getFastMsg(lang,"financeMsg5"));
        s.append(hashMap1.get("sumDay")).append("\n");
        s.append(Bot.getFastMsg(lang,"financeMsg6"));
        s.append(hashMap1.get("nameCatDay")).append("   ").append(hashMap1.get("maxDayAmount")).append("\n");
        s.append(Bot.getFastMsg(lang,"financeMsg9"));
        if ((float)hashMap.get("day")<=(float)hashMap1.get("sumDay"))
        {
            s.append(Bot.getFastMsg(lang,"financeMsg11")).append("\n\n");
        }
        else {
            s.append(Bot.getFastMsg(lang,"financeMsg12")).append("\n\n");
        }
        s.append(Bot.getFastMsg(lang,"financeMsg7"));
        s.append(hashMap1.get("sumMounth")).append("\n");
        s.append(Bot.getFastMsg(lang,"financeMsg8"));
        s.append(hashMap1.get("nameCatMounth")).append("   ").append(hashMap1.get("maxMounthAmount")).append("\n");
        s.append(Bot.getFastMsg(lang,"financeMsg9"));
        if ((float)hashMap.get("mounth") <= (float)hashMap1.get("sumMounth"))
        {
            s.append(Bot.getFastMsg(lang,"financeMsg11")).append("\n");
        }
        else {
            s.append(Bot.getFastMsg(lang,"financeMsg12")).append("\n");
        }


        sendMessage.setChatId(chatId);
        sendMessage.setText(s.toString());
        Buttons.setButtonsMain(sendMessage, chatId);

        bot.sendQueue.add(sendMessage);
        return "";
    }
}
